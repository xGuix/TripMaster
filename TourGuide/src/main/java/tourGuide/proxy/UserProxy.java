package tourGuide.proxy;

import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.dto.UserPreferencesDto;
import com.dto.UserRewardDto;
import com.model.Provider;
import com.model.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

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

    /**
     * @param userId UUID user id
     * @return return a user with this id
     */
    @RequestMapping("/getUserById")
    UserDto getUserById(@RequestParam("userId") UUID userId);

    /**
     * Gets user rewards.
     *
     * @param userName the username
     * @return list of rewards of this user
     */
    @RequestMapping("/getRewards")
    List<UserRewardDto> getUserRewards(@RequestParam("userName")String userName);

    /**
     * Add user reward.
     * @param userName   the username
     * @param userReward add the reward for this user
     */
    @RequestMapping("/addRewards")
    void addUserReward(@RequestParam("userName") String userName,@RequestParam("userRewardDto") UserRewardDto userReward);

    /**
     * Update trip deals.
     *
     * @param userName  the username
     * @param tripDeals Update trip deals for this user
     */
    @RequestMapping("/tripDeals")
    void updateTripDeals(@RequestParam("userName") String userName, List<Provider> tripDeals);

    /**
     * Get all current locations list.
     *
     * @return all current locations
     */
    @RequestMapping("/getAllCurrentLocations")
    List<UserLocationDto> getAllCurrentLocations();

    /**
     * Create visited location.
     *
     * @param userName the username
     * @param visitedLocation Add the visited location for this user
     */
    @PostMapping("/addVisitedLocation")
    void createVisitedLocation(@RequestParam("userName") String userName, VisitedLocation visitedLocation);

    /**
     * Create visited location.
     *
     * @param userName the username
     * @param userPreferences Add preferences of user
     */
    @PostMapping("/userPreferences")
    void userPreferences(@RequestParam("userName") String userName, UserPreferencesDto userPreferences);
}