package tourGuide.service;

import com.dto.NearbyAttractionsDto;
import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.Provider;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import tourGuide.proxy.UserProxy;

import java.util.*;
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
	private TripPricerProxy tripPricerProxy;

	public TrackerService trackerService;

	/**
	 *  TourGuideService constructor
	 *	Load all controller proxy
	 */
	public TourGuideService(UserProxy userProxy,
							GpsUtilProxy gpsUtilProxy,
							RewardCentralProxy rewardCentralProxy,
							TripPricerProxy tripPricerProxy) {
		this.userProxy = userProxy;
		this.gpsUtilProxy = gpsUtilProxy;
		this.rewardCentralProxy = rewardCentralProxy;
		this.tripPricerProxy = tripPricerProxy;

		trackerService = new TrackerService(this, userProxy);
		addShutDownHook();
	}

	/**
	 *  Get a user
	 *  Call to get user with user id
	 *
	 * @param userId UUID user id
	 * @return userDto The user
	 */
	public UserDto getUserById(UUID userId) {
		logger.info("Call userProxy /getUserById to search user with user id: {}", userId);
		return userProxy.getUserById(userId);
	}

	/**
	 *  Get a user
	 *  Call to get user with userName
	 *
	 * @param userName String userName
	 * @return userDto The user
	 */
	public UserDto getUser(String userName) {
		logger.info("Call userProxy /getUser to search user with username: {}", userName);
		return userProxy.getUser(userName);
	}

	/**
	 *  Get all users list
	 *  Call to get all users
	 *
	 * @return The user list
	 */
	public List<UserDto> getUsers() {
		logger.info("Call userProxy /getUsers to search for list of all users");
		return userProxy.getUsers();
	}

	/**
	 *  Add user
	 *  Call to get reward with user
	 *
	 * @param userDto String user
	 * @return userReward Rewards user list
	 */
	public void addUser(UserDto userDto) {
		logger.info("Call userProxy /addUser to add new user");
		userProxy.addUser(userDto);
	}

	/**
	 *  Get user reward List
	 *  Call to get reward with user
	 *
	 * @param userName String user
	 * @return userReward Rewards user list
	 */
	public List<UserRewardDto> getRewards(String userName) {
		logger.info("Call userProxy /getUserReward to search for list of user reward");
		return userProxy.getUserRewards(userName);
	}

	/**
	 *  Add user reward
	 *  Call to add reward for user
	 *
	 * @param userName String user
	 * @param userReward UserRewardDto reward
	 */
	public void addRewards(String userName, UserRewardDto userReward) {
		logger.info("Call userProxy /addUserReward to search for list of user reward");
		userProxy.addUserReward(userName,userReward);
	}

	/**
	 *  Get user location
	 *  Call to get location with user
	 *
	 * @param userDto User user
	 * @return visitedLocation The visited location
	 */
	public VisitedLocation getUserLocation(UserDto userDto) {
		logger.info("Call gpsUtilProxy /getUserLocation to search for user location");
		Date date = new Date();
		userDto.setLatestLocationTimestamp(date);
		return gpsUtilProxy.getUserLocation(userDto.getUserId());
	}

	/**
	 * Get all current locations:
	 * Call to get all current locations with username
	 *
	 * @return allUsers List of all users locations
	 */
	public List<UserLocationDto> getAllCurrentLocations() {
		logger.info("Get all users current Location with userProxy");
		return userProxy.getAllCurrentLocations();
	}

	/**
	 * Get trip deals:
	 * Call to get all provider list with user
	 *
	 * @param userDto UserDto user
	 * @return allProviders List of all provider
	 */
	public List<Provider> getTripDeals(UserDto userDto) {
		int cumulativeRewardPoints = 0;
		for (UserRewardDto i : userDto.getUserRewards()) {
			int rewardPoints = i.getRewardPoints();
			cumulativeRewardPoints += rewardPoints;
		}
		List<Provider> providers = tripPricerProxy.getTripDeals(tripPricerApiKey, userDto.getUserId(), userDto.getUserPreferences().getNumberOfAdults(),
				userDto.getUserPreferences().getNumberOfChildren(), userDto.getUserPreferences().getTripDuration(), cumulativeRewardPoints);
		userDto.setTripDeals(providers);
		return providers;
	}

//	/**
//	 * Get track user location:
//	 * Call to get the user visited location
//	 *
//	 * @param userDto UserDto user
//	 * @return visitedLocation User visited location
//	 */
//	public VisitedLocation trackUserLocation(UserDto userDto) {
//		RewardService rewardService = new RewardService(gpsUtilProxy,rewardCentralProxy);
//		VisitedLocation visitedLocation = gpsUtilProxy.getUserLocation(userDto.getUserId());
//		userDto.addToVisitedLocations(visitedLocation);
//		rewardCentralProxy.getDistance(userDto.getLastVisitedLocation().getLocation(),visitedLocation.getLocation());
//		rewardService.calculateRewards(userDto);
//		return visitedLocation;
//	}

	/**
	 * Get nearby attraction:
	 * Call to get the list of all visited location proximity
	 *
	 * @param userDto User visited Location
	 * @return AttractionList List of all attraction proximity
	 */
	public List<NearbyAttractionsDto> getNearbyAttractions(UserDto userDto) {
		RewardService rewardService = new RewardService(gpsUtilProxy,rewardCentralProxy);
		List<NearbyAttractionsDto> nearbyAttractionsListDto = new ArrayList<>();
		Location userLocation = getUserLocation(userDto).getLocation();
		for(Attraction attraction : gpsUtilProxy.getAttractions()) {
			if(rewardCentralProxy.isWithinAttractionProximity(attraction, userLocation)) {
				NearbyAttractionsDto nearBy = new NearbyAttractionsDto();
				nearBy.setAttraction(attraction);
				nearBy.setUserLocation(getUserLocation(userDto).getLocation());
				nearBy.setDistance(rewardCentralProxy.getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), userLocation));
				nearBy.setRewardPoints(rewardService.calculateRewards(userDto));
				nearbyAttractionsListDto.add(nearBy);
			}
		}
		return nearbyAttractionsListDto.stream().limit(5).collect(Collectors.toList());
	}

	/**
	 * Shut down hook:
	 * Call to stop tracking
	 */
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> trackerService.stopTracking()));
	}
}