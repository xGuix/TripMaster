package tourGuide.proxy;

import com.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="user" ,url="localhost:8181")
public interface UserProxy {

    /**
     * Get users list.
     * @return list of all users
     */
    @RequestMapping("/getUsers")
    List<UserDto> getUsers();

    /**
     * @param userName
     * @return return a user with this name
     */
    @RequestMapping("/getUser")
    UserDto getUser(@RequestParam("userName") String userName);

//    /**
//     * Gets user rewards.
//     *
//     * @param userName the username
//     * @return list of rewards of this user
//     */
//    @GetMapping("/rewards")
//    List<UserRewardDto> getUserRewards(String userName);
//
//    /**
//     * Create user reward.
//     *  @param userName   the username
//     * @param userReward add the reward for this user
//     */
//    @PostMapping("/rewards")
//    void createUserReward(String userName, @RequestBody UserRewardDto userReward);
//
//    /**
//     * Update trip deals.
//     *
//     * @param userName  the username
//     * @param tripDeals Update trip deals for this user
//     */
//    @PostMapping("/tripDeals")
//    void updateTripDeals(String userName, @RequestBody List<Provider> tripDeals);
//
//    /**
//     * Get all current locations list.
//     *
//     * @return all current locations
//     */
//    @GetMapping("/getAllCurrentLocations")
//    List<UserLocationDto> getAllCurrentLocations();
//
//    /**
//     * Create visited location.
//     *
//     * @param userName the username
//     * @param visitedLocation Add the visited location for this user
//     */
//    @PostMapping("/addVisitedLocation")
//    void createVisitedLocation(String userName, @RequestBody VisitedLocation visitedLocation);
//
//    /**
//     * Create visited location.
//     *
//     * @param userName the username
//     * @param userPreferences Add preferences of user
//     */
//    @PostMapping("/userPreferences")
//    void userPreferences(String userName, @RequestBody UserPreferencesDto userPreferences);
}