package tourGuide.service;

import com.dto.NearbyAttractionsDto;
import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Provider;
import com.model.VisitedLocation;
import com.helper.InternalTestDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import tourGuide.proxy.UserProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.helper.InternalTestDataSet.tripPricerApiKey;

/**
 * Tour Guide Service
 */
@Service
public class TourGuideService {

	static final Logger logger = LoggerFactory.getLogger("TourGuideServiceLog");

	private final UserProxy userProxy;
	private final GpsUtilProxy gpsUtil;
	private final RewardCentralProxy rewardCentral;
	private final TripPricerProxy tripPricer;
	public TrackerService trackerService;
	public InternalTestDataSet internalTestDataSet;


	/**
	 *  TourGuideService constructor
	 *	Load all controller proxy
	 */
	public TourGuideService(InternalTestDataSet internalTestDataSet,
							UserProxy userProxy,
							GpsUtilProxy gpsUtil,
							RewardCentralProxy rewardCentral,
							TripPricerProxy tripPricer) {
		this.internalTestDataSet = internalTestDataSet;
		this.userProxy = userProxy;
		this.gpsUtil = gpsUtil;
		this.rewardCentral = rewardCentral;
		this.tripPricer = tripPricer;

		logger.info("TestMode enabled");
		logger.debug("Initializing users");
		internalTestDataSet.initializeInternalUsers();
		logger.debug("Finished initializing users");
		trackerService = new TrackerService(this,userProxy, rewardCentral);
		addShutDownHook();
	}

	/**
	 *  Get user reward List
	 *  Call to get reward with user
	 *
	 * @param userDto User user
	 * @return userReward Rewards user list
	 */
	public List<UserRewardDto> getUserRewards(UserDto userDto) {
		return userDto.getUserRewards();
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
//
//	/**
//	 *  Get a user
//	 *  Call to get user with userName
//	 *
//	 * @param userName String userName
//	 * @return userDto The user
//	 */
//	public UserDto getUser(String userName) {
//		return internalTestDataSet.internalUserMap.get(userName);
//	}
//
//	/**
//	 *  Get all users list
//	 *  Call to get all users
//	 *
//	 * @return The user list
//	 */
//	public List<UserDto> getAllUsers() {
//		return new ArrayList<>(internalTestDataSet.internalUserMap.values());
//	}
//
//	public void addUser(UserDto userDto) {
//		if(!internalTestDataSet.internalUserMap.containsKey(userDto.getUserName())) {
//			internalTestDataSet.internalUserMap.put(userDto.getUserName(), userDto);
//		}
//	}
	
	public List<Provider> getTripDeals(UserDto userDto) {
		int cumulativeRewardPoints = 0;
		for (UserRewardDto i : userDto.getUserRewards()) {
			int rewardPoints = i.getRewardPoints();
			cumulativeRewardPoints += rewardPoints;
		}
		List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, userDto.getUserName(), userDto.getUserPreferences().getNumberOfAdults(),
				userDto.getUserPreferences().getNumberOfChildren(), userDto.getUserPreferences().getTripDuration(), cumulativeRewardPoints);
		userDto.setTripDeals(providers);
		return providers;
	}
	
	public VisitedLocation trackUserLocation(UserDto userDto) {
		Locale.setDefault(Locale.US);
		VisitedLocation visitedLocation = gpsUtil.getUserLocation(userDto.getUserId());
		userDto.addToVisitedLocations(visitedLocation);
		rewardCentral.calculateRewards(userDto);
		return visitedLocation;
	}

	public List<NearbyAttractionsDto> getNearByAttractions(VisitedLocation visitedLocation) {
		List<NearbyAttractionsDto> nearbyAttractionsListDto = new ArrayList<>();
		for(Attraction attraction : gpsUtil.getAttractions()) {
			if(rewardCentral.isWithinAttractionProximity(attraction, visitedLocation.getLocation())) {
				NearbyAttractionsDto nearBy = new NearbyAttractionsDto();
				nearBy.setAttraction(attraction);
				nearBy.setUserLocation(visitedLocation.getLocation());
				nearBy.setDistance(rewardCentral.getDistance(attraction, visitedLocation.getLocation()));
				String userDto = null;
				nearBy.setRewardPoints(rewardCentral.getRewardPoints(attraction, userDto));
				nearbyAttractionsListDto.add(nearBy);
			}
		}
		return nearbyAttractionsListDto.stream().limit(5).collect(Collectors.toList());
	}
	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> trackerService.stopTracking()));
	}
}