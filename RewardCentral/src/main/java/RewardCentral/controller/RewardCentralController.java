package RewardCentral.controller;

import RewardCentral.service.RewardCentralService;
import com.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dto.UserRewardDto;
import com.dto.UserDto;
import com.model.Attraction;

import java.util.List;

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

    /**
     * Calculate rewards list.
     *
     * @param userDto the user
     * @return the list
     */
    @PostMapping("/calculateRewards")
    public List<UserRewardDto> calculateRewards(@PathVariable UserDto userDto) {
        return rewardCentralService.calculateRewards(userDto);
    }

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param userDto   the username
     * @return the reward points
     */
    @PostMapping("/getRewardPoints")
    public int rewardPoints(@RequestBody Attraction attraction, @PathVariable UserDto userDto) {
        return rewardCentralService.getRewardPoints(attraction, userDto);
    }

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param location the location
     * @return the reward points
     */
    @PostMapping("/getAttractionProxy")
    public boolean isWithinAttractionProximity(@RequestBody Attraction attraction, @PathVariable Location location) {
        return rewardCentralService.isWithinAttractionProximity(attraction, location);
    }
}