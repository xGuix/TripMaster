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

/**
 * The type User service.
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger("UserServiceLog");

    /**
     * Instantiates a new Map for users.
     */
    Map<String, User> internalUserMap = new HashMap<>();

    /**
     * Gets the list of all users.
     *
     * @return the list of all users
     */
    public List<User> getUsers() {
        logger.info("Get all internalUserMap users");
        return new ArrayList<>(internalUserMap.values());
    }

    /**
     * Gets the user with username.
     *
     * @param userName the username
     * @return the user
     */
    public User getUser(String userName) {
        logger.info("Get internalUserMap with user: {}",userName);
        return internalUserMap.get(userName);
    }

    /**
     * Gets the user by user id.
     *
     * @param userId the user id
     * @return the user
     */
    public User getUserById(UUID userId) {
        logger.info("Get internalUserMap with user: {}",userId);
        return internalUserMap.get(userId);
    }

    /**
     * Add user.
     *
     * @param user the user
     */
    public void addUser(User user) {
        if (!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
            logger.info("{} have been added.",user.getUserName());
        }
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
        logger.info("Get all current location for all users");
        return userLocationsList;
    }

    /**
     * Gets user rewards.
     *
     * @param user the user
     * @return the rewards for this user
     */
    public List<UserRewardDto> getUserRewards(User user) {
        logger.info("Reward list for user: {}",user);
        return user.getUserRewards();
    }

    /**
     * Add user reward.
     *
     * @param userName   the username
     * @param userReward add reward for the user
     */
    public void addUserReward(String userName, UserRewardDto userReward) {
        User user = getUser(userName);
        user.getUserRewards().add(userReward);
        internalUserMap.put(userName, user);
        logger.info("Reward for user: {} have been added.",user);
    }

    /**
     * Add visited location.
     *
     * @param userName        the username
     * @param visitedLocation add the new visited location for the user
     */
    public void addVisitedLocation(String userName, VisitedLocation visitedLocation){
        getUser(userName).addToVisitedLocations(visitedLocation);
        logger.info("Visited location for username: {} have been added.",userName);
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