package RewardCentral.service;

import com.dto.UserDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

/**
 * Reward Service
 */
@Service
public class RewardCentralService {

    private final Logger logger = LoggerFactory.getLogger("RewardCentralServiceLog");
	private final RewardCentral rewardCentral = new RewardCentral();

	// Location and proximity data set in miles
	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
	private final int defaultProximityBuffer = 100;
	private int proximityBuffer = defaultProximityBuffer;
	public final int attractionProximityRange = 10000;

	/**
	 * Setter proximity buffer
	 *
	 * @param proximityBuffer int in miles
	 */
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	/**
	 * Setter default proximity buffer
	 */
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	/**
	 * Get user reward points
	 *
	 * @param attraction Attraction
	 * @param userDto UserDto
	 * @return int number of reward points
	 */
	public int getRewardPoints(Attraction attraction, UserDto userDto) {
		logger.info("Get user reward points");
		return rewardCentral.getAttractionRewardPoints(attraction.getAttractionId(), userDto.getUserId());
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
	 * Get if user is near attraction
	 *
	 * @param visitedLocation Visited Location
	 * @param attraction Attraction
	 * @return boolean true if near attractions
	 */
	public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		logger.info("Get if user is near attraction with visited location");
		return !(getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), visitedLocation.getLocation()) > proximityBuffer);
	}

	/**
	 * Get distance between two distances
	 *
	 * @param loc1 Location 1st location
	 * @param loc2 Location 2nd location
	 * @return int number of reward points
	 */
	public double getDistance(Location loc1, Location loc2) {
		logger.info("Get distance between loc1:{} and loc2:{}",loc1,loc2);
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        double nauticalMiles = 60 * Math.toDegrees(angle);
		return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
	}
}