package tourGuide.service;

import com.dto.NearbyAttractionsDto;
import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Provider;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.UserProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Tour Guide Service
 */
@Service
public class TourGuideService {

	static final Logger logger = LoggerFactory.getLogger("TourGuideServiceLog");
	public static final String tripPricerApiKey = "test-server-api-key";

	private UserProxy userProxy;
	private GpsUtilProxy gpsUtilProxy;
	private RewardCentralProxy rewardCentralProxy;

//	public TripPricerProxy tripPricer;

	@Autowired
	RewardService rewardService;

	public TrackerService trackerService;

	/**
	 *  TourGuideService constructor
	 *	Load all controller proxy
	 */
	public TourGuideService(UserProxy userProxy,
							GpsUtilProxy gpsUtilProxy,
							RewardCentralProxy rewardCentralProxy) {
		this.userProxy = userProxy;
		this.gpsUtilProxy = gpsUtilProxy;
		this.rewardCentralProxy = rewardCentralProxy;
//		this.tripPricer = tripPricer;
//
		trackerService = new TrackerService(this, userProxy);
		addShutDownHook();
	}

	/**
	 *  Get a user
	 *  Call to get user with userName
	 *
	 * @param userName String userName
	 * @return userDto The user
	 */
	public UserDto getUser(String userName) {
		logger.info("Call userProxy search user with username: {}", userName);
		return userProxy.getUser(userName);
	}

	/**
	 *  Get all users list
	 *  Call to get all users
	 *
	 * @return The user list
	 */
	public List<UserDto> getUsers() {
		logger.info("Call userProxy search for list of all users");
		return userProxy.getUsers();
	}

	/**
	 *  Get user reward List
	 *  Call to get reward with user
	 *
	 * @param userDto User user
	 * @return userReward Rewards user list
	 */
	public List<UserRewardDto> getUserRewards(UserDto userDto) {
		logger.info("Call getUserReward search for list of user reward");
		return userProxy.getUserRewards(userDto.getUserName());
	}

	/**
	 *  Get user location
	 *  Call to get location with user
	 *
	 * @param userDto User user
	 * @return visitedLocation The visited location
	 */
	public VisitedLocation getUserLocation(UserDto userDto) {
		VisitedLocation visitedLocation;
		visitedLocation = (userDto.getVisitedLocations().size() > 0) ?
			userDto.getLastVisitedLocation() :
			trackUserLocation(userDto);
		return visitedLocation;
	}

//	public List<Provider> getTripDeals(UserDto userDto) {
//		int cumulativeRewardPoints = 0;
//		for (UserRewardDto i : userDto.getUserRewards()) {
//			int rewardPoints = i.getRewardPoints();
//			cumulativeRewardPoints += rewardPoints;
//		}
//		List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, userDto.getUserName(), userDto.getUserPreferences().getNumberOfAdults(),
//				userDto.getUserPreferences().getNumberOfChildren(), userDto.getUserPreferences().getTripDuration(), cumulativeRewardPoints);
//		userDto.setTripDeals(providers);
//		return providers;
//	}

	public VisitedLocation trackUserLocation(UserDto userDto) {
		Locale.setDefault(Locale.US);
		VisitedLocation visitedLocation = gpsUtilProxy.getUserLocation(userDto.getUserId());
		userDto.addToVisitedLocations(visitedLocation);
		rewardService.calculateRewards(userDto);
		return visitedLocation;
	}

	public List<NearbyAttractionsDto> getNearByAttractions(VisitedLocation visitedLocation) {
		List<NearbyAttractionsDto> nearbyAttractionsListDto = new ArrayList<>();
		for(Attraction attraction : gpsUtilProxy.getAttractions()) {
			if(rewardCentralProxy.isWithinAttractionProximity(attraction, visitedLocation.getLocation())) {
				NearbyAttractionsDto nearBy = new NearbyAttractionsDto();
				nearBy.setAttraction(attraction);
				nearBy.setUserLocation(visitedLocation.getLocation());
				nearBy.setDistance(rewardCentralProxy.getDistance(attraction, visitedLocation.getLocation()));
				String userDto = null;
				nearBy.setRewardPoints(rewardCentralProxy.getRewardPoints(attraction, userDto));
				nearbyAttractionsListDto.add(nearBy);
			}
		}
		return nearbyAttractionsListDto.stream().limit(5).collect(Collectors.toList());
	}
	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> trackerService.stopTracking()));
	}
}