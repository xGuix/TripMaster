package tourGuide.service;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;

import java.util.List;

@Service
public class RewardService {

    private static final Logger logger = LoggerFactory.getLogger("RewardsServiceLog");

    private GpsUtilProxy gpsUtilProxy;
    private RewardCentralProxy rewardCentralProxy;

    /**
     * Set RewardService constructor with proxy
     */
    public RewardService(GpsUtilProxy gpsUtilProxy,RewardCentralProxy rewardCentralProxy) {
        this.gpsUtilProxy = gpsUtilProxy;
        this.rewardCentralProxy = rewardCentralProxy;
    }

    /**
     * Get user calculate rewards:
     * Call to get the list of user rewards
     *
     * @param userDto UserDto user
     * @return User reward List
     */
    public List<UserRewardDto> calculateRewards(UserDto userDto) {
        List<VisitedLocation> userLocations = userDto.getVisitedLocations();
        List<Attraction> attractions = gpsUtilProxy.getAttractions();

        userLocations.forEach(visitedLocation -> {
            attractions.forEach(a -> {
                if (userDto.getUserRewards().stream().noneMatch(r -> r.getAttraction().getAttractionName().equals(a.getAttractionName()))) {
                    if (rewardCentralProxy.getNearbyAttractions(visitedLocation, a)) {
                        userDto.getUserRewards().add(new UserRewardDto(visitedLocation, a, rewardCentralProxy.getRewardPoints(a, userDto)));
                    }
                }
            });
        });
        logger.info("Get list of calculate rewards for users");
        return userDto.getUserRewards();
    }
}