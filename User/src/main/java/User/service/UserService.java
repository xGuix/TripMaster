package User.service;

import User.dto.UserDto;
import User.dto.UserPreferencesDto;
import User.dto.UserRewardDto;
import User.dto.UserLocationDto;
import User.helper.InternalTestHelper;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tripPricer.Provider;


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
    private final Map<String, UserDto> internalUserMap = new HashMap<>();


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


    private void initializeInternalUsers() {
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            UserDto user = new UserDto(UUID.randomUUID(), userName, phone, email);
            //UsersHelper.generateUserLocationHistory(user);

            internalUserMap.put(userName, user);
        });
        logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    /**
     * Gets user.
     *
     * @param userName the username
     * @return the user
     */
    public UserDto getUser(String userName) {
        return internalUserMap.get(userName);
    }

    /**
     * Gets all users.
     *
     * @return the list of all users
     */
    public List<UserDto> getAllUsers() {
        return new ArrayList<>(internalUserMap.values());
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

        for (UserDto user : getAllUsers()) {
            UUID userId = user.getUserId();
            VisitedLocation userLastVisitedLocation = user.getLastVisitedLocation();
            userLocationsList.add(new UserLocationDto(userId, userLastVisitedLocation.location));
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