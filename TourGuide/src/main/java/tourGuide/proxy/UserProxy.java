package tourGuide.proxy;

import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.dto.UserPreferencesDto;
import com.dto.UserRewardDto;
import com.model.Provider;
import com.model.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient(name = "com.tourGuide.user", url = "localhost:8181")
public interface UserProxy {

    /**
     * Get users list.
     * @return list of all users
     */
    @GetMapping(value = "/allUsers")
    List<UserDto> getUsers();

    /**
     * @param userName
     * @return return a user with this name
     */
    @GetMapping(value = "/getUser")
    UserDto getUser(@PathVariable String userName);

    /**
     * Gets user rewards.
     *
     * @param userName the username
     * @return list of rewards of this user
     */
    @GetMapping(value = "/rewards")
    List<UserRewardDto> getUserRewards(@PathVariable String userName);

    /**
     * Create user reward.
     *
     * @param userName   the username
     * @param userReward add the reward for this user
     */
    @PostMapping(value = "/rewards")
    void createUserReward(@PathVariable String userName, @RequestBody UserRewardDto userReward);

    /**
     * Update trip deals.
     *
     * @param userName  the username
     * @param tripDeals Update trip deals for this user
     */
    @PostMapping(value = "/tripDeals")
    void updateTripDeals(@PathVariable String userName, @RequestBody List<Provider> tripDeals);

    /**
     * Get all current locations list.
     *
     * @return all current locations
     */
    @GetMapping(value = "/getAllCurrentLocations")
    List<UserLocationDto> getAllCurrentLocations();

    /**
     * Create visited location.
     *
     * @param userName the username
     * @param visitedLocation Add the visited location for this user
     */
    @PostMapping(value = "/addVisitedLocation")
    void createVisitedLocation(@PathVariable String userName, @RequestBody VisitedLocation visitedLocation);

    /**
     * Create visited location.
     *
     * @param userName the username
     * @param userPreferences Add preferences of user
     */
    @PostMapping(value = "/userPreferences")
    void userPreferences(@PathVariable String userName, @RequestBody UserPreferencesDto userPreferences);
}