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

    private Logger logger = LoggerFactory.getLogger("RewardCentralServiceLog");
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	private final int defaultProximityBuffer = 100;
	private int proximityBuffer = defaultProximityBuffer;
	public final int attractionProximityRange = 10000;

	private RewardCentral rewardCentral;
	public RewardCentralService(RewardCentral rewardCentral) {
		this.rewardCentral = rewardCentral;
	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

//	public List<UserRewardDto> calculateRewards(UserDto userDto) {
//		List<VisitedLocation> userLocations = userDto.getVisitedLocations();
//		List<Attraction> attractions = Collections.singletonList((Attraction) gpsUtil.getAttractions());
//
//		userLocations.forEach(visitedLocation -> {
//			attractions.forEach(a -> {
//				if (userDto.getUserRewards().stream().noneMatch(r -> r.getAttraction().getAttractionName().equals(a.getAttractionName()))) {
//					if (nearAttraction(visitedLocation, a)) {
//						userDto.getUserRewards().add(new UserRewardDto(visitedLocation, a, getRewardPoints(a, userDto)));
//					}
//				}
//			});
//		});
//		return userDto.getUserRewards();
//	}

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return !(getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), location) > attractionProximityRange);
	}

	public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return !(getDistance(new Location(attraction.getLongitude(),attraction.getLatitude()), visitedLocation.getLocation()) > proximityBuffer);
	}

	public int getRewardPoints(Attraction attraction, UserDto userDto) {
		return rewardCentral.getAttractionRewardPoints(attraction.getAttractionId(), userDto.getUserId());
	}

	public double getDistance(Location loc1, Location loc2) {
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