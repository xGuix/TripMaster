package User.service;

import User.model.User;
import User.model.UserPreferences;
import com.dto.UserLocationDto;
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
    Map<String, User> internalUserMap = new HashMap<>();

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
            User user = new User(UUID.randomUUID(), userName, phone, email);

            internalUserMap.put(userName, user);
        });
        logger.debug("Created {} internal test users.", internalUserNumber);
    }

    /**
     * Gets all users.
     *
     * @return the list of all users
     */
    public List<User> getUsers() {
        logger.info("Get all internalUserMap users");
        return new ArrayList<User>(internalUserMap.values());
    }

    /**
     * Gets user.
     *
     * @param userName the username
     * @return the user
     */
    public User getUser(String userName) {
        logger.info("Get internalUserMap with user: {}",userName);
        return internalUserMap.get(userName);
    }

    /**
     * Add user.
     *
     * @param user the user
     */
    public void addUser(User user) {
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
    public List<UserRewardDto> getUserRewards(User user) {
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

        for (User user : getUsers()) {
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
        User user = getUser(userName);
        user.setTripDeals(tripDeals);
    }

    /**
     * @param userName
     * @param userPreferences
     */
    public void UpdateUserPreferences(String userName, UserPreferences userPreferences){
        User user = getUser(userName);
        user.setUserPreferences(userPreferences);
    }
}