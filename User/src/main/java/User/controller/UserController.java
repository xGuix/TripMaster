package User.controller;

import User.model.User;
import User.model.UserPreferences;
import com.dto.UserDto;
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
import java.util.UUID;

/**
 * The type User controller.
 */
@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger("UserControllerLog");

    @Autowired
    UserService userService;

    /**
     *  Get Index Controller
     *
     * @return String Greetings from User!
     */
    @RequestMapping("/")
    public String index() {
        logger.info("Get user index");
        return "Greetings from User!";
    }

    /**
     * Get All user:
     * Call to get All user with username
     *
     * @return userList List of all users
     */
    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        logger.info("Get list of all users");
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
        logger.info("Get user with username: {}", userName);
        return userService.getUser(userName);
    }

    /**
     * Get user:
     * Call to get user with id
     *
     * @param userId String userName
     * @return userName User userName
     */
    @RequestMapping("/getUserById")
    public User getUserById(@RequestParam UUID userId) {
        logger.info("Get user with userId: {}", userId);
        return userService.getUserById(userId);
    }

    /**
     * Get all current locations list.
     *
     * @return all current locations
     */
    @RequestMapping("/getAllCurrentLocations")
    public List<UserLocationDto> getAllCurrentLocations(){
        logger.info("Get all list of current location users");
        return userService.getAllCurrentLocations();
    }

    /**
     * Gets user rewards.
     *
     * @param userName the username
     * @return list of rewards of this user
     */
    @RequestMapping("/getRewards")
    public List<UserRewardDto> getUserRewards(@RequestParam String userName) {
        logger.info("Get user rewards with userName: {}",userName);
        User user = getUser(userName);
        return userService.getUserRewards(user);
    }

    /**
     * Create user reward.
     *
     * @param userName   the username
     * @param userReward add the reward for this user
     */
    @PutMapping("/addRewards")
    public void addUserReward(@RequestParam String userName, @RequestBody UserRewardDto userReward){
        logger.info("Add user reward to userName: {} {}",userName, userReward);
        userService.addUserReward(userName, userReward);
    }

    /**
     * Update trip deals.
     *
     * @param userName the username
     * @param tripDeals Update trip deals for this user
     */
    @PostMapping("/tripDeals")
    public void updateTripDeals(@RequestParam String userName, @RequestBody List<Provider> tripDeals){
        userService.updateTripDeals(userName, tripDeals);
    }

    /**
     * Create visited location.
     *
     * @param userName the username
     * @param visitedLocation Add the visited location for this user
     */
    @PostMapping("/addVisitedLocation")
    public void createVisitedLocation(@RequestParam String userName, @RequestBody VisitedLocation visitedLocation){
        userService.addVisitedLocation(userName, visitedLocation);
    }

    /**
     * Update User preferences.
     *
     * @param userName the username
     * @param userPreferences The new user preferences
     */
    @PostMapping("/userPreferences")
    public void userPreferences(@RequestParam String userName, @RequestBody UserPreferences userPreferences){
        userService.UpdateUserPreferences(userName, userPreferences);
    }
}