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

import java.util.Collections;
import java.util.List;

@Service
public class RewardService {

    private static final Logger logger = LoggerFactory.getLogger("RewardsServiceLog");

    private GpsUtilProxy gpsUtilProxy;
    private RewardCentralProxy rewardCentralProxy;

    public RewardService( GpsUtilProxy gpsUtilProxy,RewardCentralProxy rewardCentralProxy) {
        this.gpsUtilProxy = gpsUtilProxy;
        this.rewardCentralProxy = rewardCentralProxy;
    }

    public List<UserRewardDto> calculateRewards(UserDto userDto) {
        List<VisitedLocation> userLocations = userDto.getVisitedLocations();
        List<Attraction> attractions = Collections.singletonList((Attraction) gpsUtilProxy.getAttractions());

        userLocations.forEach(visitedLocation -> {
            attractions.forEach(a -> {
                if (userDto.getUserRewards().stream().noneMatch(r -> r.getAttraction().getAttractionName().equals(a.getAttractionName()))) {
                    if (rewardCentralProxy.nearAttraction(visitedLocation, a)) {
                        userDto.getUserRewards().add(new UserRewardDto(visitedLocation, a, rewardCentralProxy.getRewardPoints(a, String.valueOf(userDto))));
                    }
                }
            });
        });
        return userDto.getUserRewards();
    }

}