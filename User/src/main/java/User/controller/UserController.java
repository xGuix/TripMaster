package User.controller;

import User.model.User;
import User.model.UserPreferences;
import com.dto.UserLocationDto;
import com.dto.UserRewardDto;
import User.service.UserService;
import com.model.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.model.Provider;

import java.util.List;

/**
 * The type User controller.
 */
@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger("UserControllerLog");

    @Autowired
    UserService userService;

    /**
     * Get All user:
     * Call to get All user with username
     *
     * @return userList List of all users
     */
    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        logger.info("Search list of all users");
        return userService.getUsers();
    }

    /**
     * Get user:
     * Call to get user with username
     *
     * @param userName String userName
     * @return userName User userName
     */
    @RequestMapping("/getUser")
    public User getUser(@RequestParam String userName) {
        logger.info("Search user with username: {}", userName);
        return userService.getUser(userName);
    }

    /**
     * Gets user rewards.
     *
     * @param userName the username
     * @return list of rewards of this user
     */
    @GetMapping("/rewards")
    public List<UserRewardDto> getUserRewards(String userName) {
        return userService.getUserRewards(getUser(userName));
    }

    /**
     * Create user reward.
     *
     * @param userName   the username
     * @param userReward add the reward for this user
     */
    @PostMapping("/rewards")
    public void createUserReward(String userName, @RequestBody UserRewardDto userReward){
        userService.addUserReward(userName, userReward);
    }

    /**
     * Update trip deals.
     *
     * @param userName the username
     * @param tripDeals Update trip deals for this user
     */
    @PostMapping("/tripDeals")
    public void updateTripDeals(String userName, @RequestBody List<Provider> tripDeals){
        userService.updateTripDeals(userName, tripDeals);
    }

    /**
     * Get all current locations list.
     *
     * @return all current locations
     */
    @GetMapping("/getAllCurrentLocations")
    public List<UserLocationDto> getAllCurrentLocations(){
        return userService.getAllCurrentLocations();
    }

    /**
     * Create visited location.
     *
     * @param userName the username
     * @param visitedLocation Add the visited location for this user
     */
    @PostMapping("/addVisitedLocation")
    public void createVisitedLocation(String userName, @RequestBody VisitedLocation visitedLocation){
        userService.addVisitedLocation(userName, visitedLocation);
    }

    /**
     * Update User preferences.
     *
     * @param userName the username
     * @param userPreferences The new user preferences
     */
    @PostMapping("/userPreferences")
    public void userPreferences(String userName, @RequestBody UserPreferences userPreferences){
        userService.UpdateUserPreferences(userName, userPreferences);
    }
}