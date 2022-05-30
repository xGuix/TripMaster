package tourGuide.proxy;

import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "rewardCentral", url = "localhost:8383")
public interface RewardCentralProxy {

//    /**
//     * Calculate rewards list.
//     *
//     * @param userDto the username
//     * @return the list
//     */
//    @RequestMapping(value="/calculateRewards")
//    List<UserRewardDto> calculateRewards(@RequestParam("user")UserDto userDto);

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param userName the username
     * @return the reward points
     */
    @RequestMapping("/getRewardPoints")
    int getRewardPoints(@RequestParam("attraction")Attraction attraction, String userName);

    /**
     * Gets distance.
     *
     * @param attraction the location
     * @param location the visited location
     * @return double distance
     */
    @RequestMapping("/getDistance")
    double getDistance(@RequestParam("attraction") Attraction attraction, Location location);

    /**
     * Gets true if attractions around.
     *
     * @param attraction the attraction
     * @param location the location
     * @return boolean attraction proximity
     */
    @RequestMapping("/getAttractionProxy")
    boolean isWithinAttractionProximity(@RequestParam("attraction") Attraction attraction,Location location);

    /**
     * Gets true if visited attraction around.
     *
     * @param visitedLocation visited location
     * @param attraction the attraction
     * @return boolean Visited location proximity
     */
    @RequestMapping("/getNearAttraction")
    boolean nearAttraction(@RequestParam("attraction") VisitedLocation visitedLocation, Attraction attraction);
}