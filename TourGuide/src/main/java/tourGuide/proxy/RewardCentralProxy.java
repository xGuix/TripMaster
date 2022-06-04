package tourGuide.proxy;

import com.dto.UserDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "rewardCentral", url = "localhost:8383")
public interface RewardCentralProxy {

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param userDto the user
     * @return the reward points
     */
    @RequestMapping("/getRewardPoints")
    int getRewardPoints(@RequestParam("attraction")Attraction attraction, @RequestParam("userDto") UserDto userDto);

    /**
     * Gets distance.
     *
     * @param location1 the location
     * @param location2 the visited location
     * @return double distance
     */
    @RequestMapping("/getDistance")
    double getDistance(@RequestParam("location") Location location1,@RequestParam("location") Location location2);

    /**
     * Gets true if attractions around.
     *
     * @param attraction the attraction
     * @param location the location
     * @return boolean attraction proximity
     */
    @RequestMapping("/getAttractionProxy")
    boolean isWithinAttractionProximity(@RequestParam("attraction") Attraction attraction,@RequestParam("location") Location location);

    /**
     * Gets true if visited attraction around.
     *
     * @param visitedLocation visited location
     * @param attraction the attraction
     * @return boolean Visited location proximity
     */
    @RequestMapping("/getNearbyAttractions")
    boolean getNearbyAttractions(@RequestParam("visitedLocation") VisitedLocation visitedLocation, @RequestParam("attraction") Attraction attraction);
}