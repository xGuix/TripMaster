package RewardCentral.controller;

import RewardCentral.service.RewardCentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.dto.UserRewardDto;
import com.dto.UserDto;
import com.model.Attraction;

import java.util.List;

@RestController
public class RewardCentralController {

    @Autowired
    public RewardCentralService rewardCentralService;

    /**
     * Instantiates a new Rewards controller.
     *
     * @param rewardCentralService the rewards service
     */
    public RewardCentralController(RewardCentralService rewardCentralService){
        this.rewardCentralService = rewardCentralService;
    }

    /**
     * Calculate rewards list.
     *
     * @param userDto the user
     * @return the list
     */
    @PostMapping("/calculateRewards/{userName}")
    public List<UserRewardDto> calculateRewards(@PathVariable UserDto userDto) {
        return rewardCentralService.calculateRewards(userDto);
    }

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param userName   the username
     * @return the reward points
     */
    @PostMapping("/getRewardPoints/{userName}")
    public int getRewardPoints(@RequestBody Attraction attraction, @PathVariable String userName) {
        return rewardCentralService.getAttractionRewardPoints(attraction, userName);
    }
}