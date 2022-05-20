package RewardCentral.controller;

import RewardCentral.service.RewardsService;
import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RewardCentralController {

    @Autowired
    public RewardsService rewardsService;

    /**
     * Instantiates a new Rewards controller.
     *
     * @param rewardsService the rewards service
     */
    public RewardCentralController(RewardsService rewardsService){
        this.rewardsService = rewardsService;
    }

    /**
     * Calculate rewards list.
     *
     * @param userDto the user
     * @return the list
     */
    @PostMapping("/calculateRewards/{userName}")
    public List<UserRewardDto> calculateRewards(@PathVariable UserDto userDto) {
        return rewardsService.calculateRewards(userDto);
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
        return rewardsService.getAttractionRewardPoints(attraction, userName);
    }
}