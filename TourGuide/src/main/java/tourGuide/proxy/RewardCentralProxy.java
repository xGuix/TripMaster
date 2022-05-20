package tourGuide.proxy;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "rewardCentral", url = "localhost:8383")
public interface RewardCentralProxy {
    /**
     * Calculate rewards list.
     *
     * @param userDto the user name
     * @return the list
     */
    @PostMapping("/calculateRewards/{userName}")
    List<UserRewardDto> calculateRewards(@PathVariable UserDto userDto);

    /**
     * Gets reward points.
     *
     * @param attraction the attraction
     * @param userName the user name
     * @return the reward points
     */
    @PostMapping("/getRewardPoints/{userName}")
    int getRewardPoints(@RequestBody Attraction attraction, @PathVariable String userName);

    /**
     * Gets distance.
     *
     * @param attraction the location
     * @param location the visited location
     * @return double distance
     */
    @PostMapping("/getDistance")
    double getDistance(@RequestBody Attraction attraction, Location location);

    @PostMapping("/getRewardPoints/{userName}")
    boolean isWithinAttractionProximity(Attraction attraction, Location location);
}