package tourGuide.service;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.springframework.stereotype.Service;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;

import java.util.List;

/**
 * Reward Service
 */
@Service
public class RewardsService {

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
    private final int defaultProximityBuffer = 100;
	private int proximityBuffer = defaultProximityBuffer;
	public final int attractionProximityRange = 10000;
	private GpsUtilProxy gpsUtil;
	private final RewardCentralProxy rewardsCentral;

	public RewardsService(GpsUtilProxy gpsUtil, RewardCentralProxy rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public List<UserRewardDto> calculateRewards(UserDto userDto) {
		List<VisitedLocation> userLocations = userDto.getVisitedLocations();
		List<Attraction> attractions = gpsUtil.getAttractions();

		userLocations.forEach(visitedLocation -> {
			attractions.forEach(a -> {
				if (userDto.getUserRewards().stream().noneMatch(r -> r.getAttraction().getAttractionName().equals(a.getAttractionName()))) {
					if (nearAttraction(visitedLocation, a)) {
						userDto.getUserRewards().add(new UserRewardDto(visitedLocation, a, getRewardPoints(a, userDto)));
					}
				}
			});
		});
		return userDto.getUserRewards();
	}

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return !(getDistance(new Location(attraction.getLatitude(),attraction.getLongitude()), location) > attractionProximityRange);
	}

	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return !(getDistance(new Location(attraction.getLatitude(),attraction.getLongitude()), visitedLocation.getLocation()) > proximityBuffer);
	}

	private int getRewardPoints(Attraction attraction, UserDto userDto) {
		return rewardsCentral.getRewardPoints(attraction, userDto.getUserName());
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