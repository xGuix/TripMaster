package tourGuide.service;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.UserProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class RewardService {

    private static final Logger logger = LoggerFactory.getLogger("RewardServiceLog");
    Executor executor = Executors.newFixedThreadPool(500);

    @Autowired
    GpsUtilProxy gpsUtilProxy;
    @Autowired
    RewardCentralProxy rewardCentralProxy;

    // Location and proximity data set in miles
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private int proximityBuffer = 100;
    public final int attractionProximityRange = 9999;

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
		logger.info("Get isWithinAttractionProximity with attraction: {}", attraction);
        logger.info("Get isWithinAttractionProximity with user location: {}", location);
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
        logger.info("Get nearAttraction with User visited Location {}",visitedLocation);
        logger.info("Get nearAttraction with attraction {}",attraction);
        return !(getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), visitedLocation.getLocation()) > proximityBuffer);
    }

    /**
     * Get user calculate rewards:
     * Call to get the list of user rewards
     *
     * @param userDto UserDto user
     * @return User reward List
     */
    public CompletableFuture<?> calculateRewards(UserDto userDto) {
        List<VisitedLocation> visitedLocations = userDto.getVisitedLocations();
        List<UserRewardDto> userRewardList = userDto.getUserRewards();
        List<Attraction> attractions = gpsUtilProxy.getAttractions().parallelStream()
                .filter(attrac -> userRewardList.stream()
                .noneMatch(rew -> rew.getAttraction().getAttractionName().equals(attrac.getAttractionName()))).collect(Collectors.toList());

        return CompletableFuture.runAsync(() -> {
            visitedLocations.forEach(visited -> attractions.forEach(att -> {
                    if(nearAttraction(visited,att)){
                        userDto.addUserReward(new UserRewardDto(visited, att, rewardCentralProxy.getRewardPoints(att.getAttractionId(), userDto.getUserId())));
                        logger.info("Get user: {}", userDto.getUserName());
                        logger.info("Add user rewards: {}", userDto.getUserRewards());
                    }
                }));
        },executor);
    }
}