package RewardCentral.controller;

import RewardCentral.service.RewardCentralService;
import com.model.Location;
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

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param userDto   the username
     * @return the reward points
     */
    @RequestMapping("/getRewardPoints")
    public int getRewardPoints(Attraction attraction, UserDto userDto) {
        logger.info("Get reward points from rewardCentral");
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
        logger.info("Get within attraction proximity from rewardCentral");
        return rewardCentralService.isWithinAttractionProximity(attraction, location);
    }
}