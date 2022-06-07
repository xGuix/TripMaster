package RewardCentral.controller;

import RewardCentral.service.RewardCentralService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
     * @param attractionId the attraction
     * @param userId   the username
     * @return the reward points
     */
    @RequestMapping("/getRewardPoints")
    public int getRewardPoints(@RequestParam UUID attractionId,@RequestParam UUID userId) {
        logger.info("Get rewardCentral controller to send service with attractionId and userID", attractionId, userId);
        return rewardCentralService.getRewardPoints(attractionId, userId);
    }
}