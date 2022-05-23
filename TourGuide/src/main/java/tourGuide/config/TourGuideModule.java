package tourGuide.config;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;

import java.util.List;
import java.util.UUID;

@Configuration
public class TourGuideModule {
    /**
     * Gets gps util.
     *
     * @return GpsUtil gps util
     */
    @Bean
    public GpsUtilProxy getGpsUtil() {
        return new GpsUtilProxy() {
            @Override
            public List<Attraction> getAttractions() {
                return null;
            }

            @Override
            public VisitedLocation getUserLocation(UUID userId) {
                return null;
            }
        };
    }

    /**
     * Gets reward central.
     *
     * @return RewardCentral reward central
     */
    @Bean
    public RewardCentralProxy getRewardCentral() {
        return new RewardCentralProxy() {
            @Override
            public List<UserRewardDto> calculateRewards(UserDto userDto) {
                return null;
            }

            @Override
            public int getRewardPoints(Attraction attraction, String userName) {
                return 0;
            }

            @Override
            public double getDistance(Attraction attraction, Location location) {
                return 0;
            }

            @Override
            public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
                return false;
            }
        };
    }

}