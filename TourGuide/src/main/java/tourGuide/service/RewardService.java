package tourGuide.service;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;

import java.util.Arrays;
import java.util.List;

@Service
public class RewardService {

    private static final Logger logger = LoggerFactory.getLogger("RewardsServiceLog");
    private GpsUtilProxy gpsUtilProxy;
    private RewardCentralProxy rewardCentralProxy;

    // Location and proximity data set in miles
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private int proximityBuffer = 100;
    public final int attractionProximityRange = 1000;


    /**
     * Set RewardService constructor with proxy
     */
    public RewardService(GpsUtilProxy gpsUtilProxy,RewardCentralProxy rewardCentralProxy) {
        this.gpsUtilProxy = gpsUtilProxy;
        this.rewardCentralProxy = rewardCentralProxy;
    }

    /**
	 * Get if user is within attraction proximity
	 *
	 * @param attraction Attraction
	 * @param location Location
	 * @return boolean true if attractions proximity
	 */
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		logger.info("Get if user is within attraction proximity with current Location");
		return !(getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), location) > attractionProximityRange);
	}

    /**
     * Get distance between two distances
     *
     * @param locOne Location 1st location
     * @param locTwo Location 2nd location
     * @return int number of reward points
     */
    public double getDistance(Location locOne, Location locTwo) {
        logger.info("Get distance between locOne:{} and loc2:{}",locOne,locTwo);
        double lat1 = Math.toRadians(locOne.getLatitude());
        double lon1 = Math.toRadians(locOne.getLongitude());
        double lat2 = Math.toRadians(locTwo.getLatitude());
        double lon2 = Math.toRadians(locTwo.getLongitude());
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }

    /**
     * Get if user is near attraction
     *
     * @param visitedLocation VisitedLocation User location
     * @param attraction Attraction
     * @return boolean true if near attractions
     */
    public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
        logger.info("Get if user is near attraction with visited location");
        return !(getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), visitedLocation.getLocation()) > proximityBuffer);
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
        VisitedLocation userLocation = gpsUtilProxy.getUserLocation(userDto.getUserId());
        userLocations.forEach(visitedLocation -> {
            attractions.forEach(a -> {
                if (userDto.getUserRewards().stream().noneMatch(r -> r.getAttraction().getAttractionName().equals(a.getAttractionName()))) {
                    if (nearAttraction(userLocation, a)) {
                        userDto.getUserRewards().add(new UserRewardDto(visitedLocation, a, rewardCentralProxy.getRewardPoints(a, userDto)));
                    }
                }
            });
        });
        logger.info("Get list of calculate rewards for users");
        return userDto.getUserRewards();
    }
}