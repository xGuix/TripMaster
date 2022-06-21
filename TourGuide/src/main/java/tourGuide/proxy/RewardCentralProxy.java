package tourGuide.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(value = "rewardCentral", url = "${tourguide.microservice-rewardcentral}")
public interface RewardCentralProxy {
    /**
     * Gets reward points.
     *
     * @param attractionId UUID attraction id
     * @param userId UUID user id
     * @return the reward points
     */
    @RequestMapping("/getRewardPoints")
    int getRewardPoints(@RequestParam("attractionId") UUID attractionId, @RequestParam("userId") UUID userId);
}