package User.controller;

import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.dto.UserPreferencesDto;
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

//    /**
//     * Instantiates a new User controller.
//     *
//     * @param userService the user service
//     */
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }


    /**
     * Get All user:
     * Call to get All user with username
     *
     * @return userList List of all users
     */
    @RequestMapping("/allUsers")
    public List<UserDto> getAllUsers() {
        logger.info("Search list of all users");
        return userService.getAllUsers();
    }

    /**
     * Get user:
     * Call to get user with username
     *
     * @param userName String userName
     * @return userName User userName
     */
    @RequestMapping("/getUser")
    public UserDto getUser(@PathVariable String userName) {
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
    public List<UserRewardDto> getUserRewards(@PathVariable String userName) {
        return userService.getUserRewards(getUser(userName));
    }

    /**
     * Create user reward.
     *
     * @param userName   the username
     * @param userReward add the reward for this user
     */
    @PostMapping("/rewards")
    public void createUserReward(@PathVariable String userName, @RequestBody UserRewardDto userReward){
        userService.addUserReward(userName, userReward);
    }

    /**
     * Update trip deals.
     *
     * @param userName the username
     * @param tripDeals Update trip deals for this user
     */
    @PostMapping("/tripDeals")
    public void updateTripDeals(@PathVariable String userName, @RequestBody List<Provider> tripDeals){
        userService.updateTripDeals(userName, tripDeals);
    }

//    /**
//     * Get all current locations list.
//     *
//     * @return all current locations
//     */
//    @GetMapping("/getAllCurrentLocations")
//    public List<UserLocationDto> getAllCurrentLocations(){
//        return userService.getAllCurrentLocations();
//    }

    /**
     * Create visited location.
     *
     * @param userName the username
     * @param visitedLocation Add the visited location for this user
     */
    @PostMapping("/addVisitedLocation")
    public void createVisitedLocation(@PathVariable String userName, @RequestBody VisitedLocation visitedLocation){
        userService.addVisitedLocation(userName, visitedLocation);
    }

    /**
     * Update User preferences.
     *
     * @param userName the username
     * @param userPreferences The new user preferences
     */
    @PostMapping("/userPreferences")
    public void userPreferences(@PathVariable String userName, @RequestBody UserPreferencesDto userPreferences){
        userService.UpdateUserPreferences(userName, userPreferences);
    }
}