package User.service;

import User.model.User;
import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.dto.UserPreferencesDto;
import com.dto.UserRewardDto;
import com.model.Provider;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

/**
 * The type User service.
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * The Test mode.
     */
    boolean testMode = true;
    private static int internalUserNumber = 100;

    /**
     * Instantiates a new Map for user.
     */
    Map<String, UserDto> internalUserMap = new HashMap<>();

    /**
     * Instantiates a new User service.
     */
    public UserService(){
        if (testMode) {
            logger.info("TestMode enabled");
            logger.debug("Initializing users");
            initializeInternalUsers();
            logger.debug("Finished initializing users");

        }
    }

    /**
     * Instantiates a new User list.
     */
    private void initializeInternalUsers() {
        IntStream.range(0, internalUserNumber).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            UserDto user = new UserDto(UUID.randomUUID(), userName, phone, email);

            internalUserMap.put(userName, user);
        });
        logger.debug("Created {} internal test users.", internalUserNumber);
    }

    /**
     * Gets all users.
     *
     * @return the list of all users
     */
    public List<UserDto> getUsers() {
        logger.info("Get all internalUserMap users");
        return new ArrayList<>(internalUserMap.values());
    }

    /**
     * Gets user.
     *
     * @param userName the username
     * @return the user
     */
    public UserDto getUser(String userName) {
        logger.info("Get internalUserMap with user: {}",userName);
        return internalUserMap.get(userName);
    }

    /**
     * Add user.
     *
     * @param user the user
     */
    public void addUser(UserDto user) {
        if (!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
        }
    }

    /**
     * Gets user rewards.
     *
     * @param user the user
     * @return the rewards for this user
     */
    public List<UserRewardDto> getUserRewards(UserDto user) {
        return user.getUserRewards();
    }

    /**
     * Add user reward.
     *
     * @param userName   the username
     * @param userReward add reward for the user
     */
    public void addUserReward(String userName, UserRewardDto userReward) {
        getUser(userName).addUserReward(userReward);
    }

    /**
     * Add visited location.
     *
     * @param userName        the username
     * @param visitedLocation add the new visited location for the user
     */
    public void addVisitedLocation(String userName, VisitedLocation visitedLocation){
        getUser(userName).addToVisitedLocations(visitedLocation);
    }


    /**
     * Gets all current locations.
     *
     * @return the localisation of all users
     */
    public List<UserLocationDto> getAllCurrentLocations() {
        List<UserLocationDto> userLocationsList = new ArrayList<>();

        for (UserDto user : getUsers()) {
            UUID userId = user.getUserId();
            VisitedLocation userLastVisitedLocation = user.getLastVisitedLocation();
            userLocationsList.add(new UserLocationDto(userId, userLastVisitedLocation.getLocation()));
        }
        return userLocationsList;
    }

    /**
     * Update trip deals.
     *
     * @param userName  the username
     * @param tripDeals List of Provider
     */
    public void updateTripDeals(String userName, List<Provider> tripDeals) {
        UserDto user = getUser(userName);
        user.setTripDeals(tripDeals);
    }

    /**
     * @param userName
     * @param userPreferences
     */
    public void UpdateUserPreferences(String userName, UserPreferencesDto userPreferences){
        UserDto user = getUser(userName);
        user.setUserPreferences(userPreferences);
    }
}