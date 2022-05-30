package RewardCentral.controller;

import RewardCentral.service.RewardCentralService;
import com.model.Location;
import com.model.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dto.UserDto;
import com.model.Attraction;

@RestController
public class RewardCentralController {

    private static final Logger logger = LogManager.getLogger("RewardCentralControllerLog");

    /**
     *  Load RewardCentralService
     */
    @Autowired
    RewardCentralService rewardCentralService;

    /**
     *  Get Index Controller
     *
     * @return String Greetings from RewardCentral!
     */
    @RequestMapping("/")
    public String index() {
        logger.info("Get reward central index");
        return "Greetings from RewardCentral!";
    }

//    /**
//     * Calculate rewards list.
//     *
//     * @param userDto the user
//     * @return the list
//     */
//    @PostMapping("/calculateRewards")
//    public List<UserRewardDto> calculateRewards(@RequestParam UserDto userDto) {
//        return rewardCentralService.calculateRewards(userDto);
//    }

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param userDto   the username
     * @return the reward points
     */
    @RequestMapping("/getRewardPoints")
    public int getRewardPoints(Attraction attraction, UserDto userDto) {
        return rewardCentralService.getRewardPoints(attraction, userDto);
    }

    /**
     * Gets attractions proximity.
     *
     * @param attraction the attraction
     * @param location the location
     * @return boolean
     */
    @RequestMapping("/getAttractionProxy")
    public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
        return rewardCentralService.isWithinAttractionProximity(attraction, location);
    }

    /**
     * Gets visited attraction around.
     *
     * @param visitedLocation visited location
     * @param attraction the attraction
     * @return boolean Visited location proximity
     */
    @RequestMapping("/getNearAttraction")
    public boolean nearAttraction(VisitedLocation visitedLocation,Attraction attraction) {
        return rewardCentralService.nearAttraction(visitedLocation, attraction);
    }

    /**
     * Gets attractions proximity.
     *
     * @param loc1 the location 1
     * @param loc2 the location 2
     * @return boolean
     */
    @RequestMapping("/getDistance")
    public double getDistance(Location loc1, Location loc2) {
        return rewardCentralService.getDistance(loc1, loc2);
    }
}