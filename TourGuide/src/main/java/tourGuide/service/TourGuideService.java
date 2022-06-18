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
import tourGuide.util.InternalTestDataSet;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static tourGuide.util.InternalTestDataSet.tripPricerApiKey;

/**
 * Tour Guide Service
 */
@Service
public class TourGuideService {

	static final Logger logger = LoggerFactory.getLogger("TourGuideServiceLog");
	ExecutorService executor = Executors.newFixedThreadPool(100);

	private final UserProxy userProxy;
	private final GpsUtilProxy gpsUtilProxy;
	private final RewardCentralProxy rewardCentralProxy;
	private final TripPricerProxy tripPricerProxy;
	public InternalTestDataSet internalTestDataSet;
	public RewardService rewardService;
	public TrackerService trackerService;


	/**
	 *  TourGuideService constructor
	 *	Load all controller proxy
	 */
	public TourGuideService(InternalTestDataSet internalTestDataSet,
							UserProxy userProxy,
							GpsUtilProxy gpsUtilProxy,
							RewardCentralProxy rewardCentralProxy,
							TripPricerProxy tripPricerProxy) {
		this.internalTestDataSet= internalTestDataSet;
		this.userProxy = userProxy;
		this.gpsUtilProxy = gpsUtilProxy;
		this.rewardCentralProxy = rewardCentralProxy;
		this.tripPricerProxy = tripPricerProxy;

		internalTestDataSet.initializeInternalUsers();
		logger.debug("Initializing {} users", userProxy.getUsers().size());

		trackerService = new TrackerService(this, userProxy);
		rewardService = new RewardService(gpsUtilProxy,rewardCentralProxy);
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
	 * @param userId User user id
	 * @return visitedLocation The visited location
	 */
	public VisitedLocation getUserLocation(UUID userId) {
		logger.info("Call gpsUtilProxy /getUserLocation to search for user visited location");
		return gpsUtilProxy.getUserLocation(userId);
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
		logger.info("Get Trip Deal for user: {} with tripDeal: {}", userDto.getUserName(), userDto.getTripDeals());
		return providers;
	}

	/**
	 * Get track of user location:
	 * Call to track the user location with user
	 *
	 * @param userId UserDto user
	 * @return visitedLocation The actual visited location
	 */
	public CompletableFuture<?> trackUserLocation(UUID userId) {
		logger.info("Get tracking of user with id: {}", userId);
		return CompletableFuture.runAsync(()-> gpsUtilProxy.getUserLocation(userId),executor);
	}

	/**
	 * Get nearby attraction:
	 * Call to get the list of all visited location proximity
	 *
	 * @param userId UUID user id
	 * @return AttractionList List of all attraction proximity
	 */
	public List<NearbyAttractionsDto> getNearbyAttractions(UUID userId) {
		List<NearbyAttractionsDto> nearbyAttractionsListDto = new ArrayList<>();
		VisitedLocation userLocation = getUserLocation(userId);
		List<Attraction> attractionList = gpsUtilProxy.getAttractions();
		for(Attraction attraction : attractionList) {
			if(rewardService.isWithinAttractionProximity(attraction, userLocation.getLocation())) {
				NearbyAttractionsDto nearBy = new NearbyAttractionsDto();
				nearBy.setAttraction(attraction);
				nearBy.setUserLocation(userLocation.getLocation());
				nearBy.setDistance(rewardService.getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), userLocation.getLocation()));
				nearBy.setRewardPoints(rewardCentralProxy.getRewardPoints(attraction.getAttractionId(), userId));
				nearbyAttractionsListDto.add(nearBy);
				logger.info("Get nearby attractions with user id: {}", userId);
				logger.info("Get nearby attractions: {}", nearBy);
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