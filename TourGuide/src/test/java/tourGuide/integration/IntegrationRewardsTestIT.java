package tourGuide.integration;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import com.util.InternalTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import tourGuide.proxy.UserProxy;
import tourGuide.service.RewardService;
import tourGuide.service.TourGuideService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class IntegrationRewardsTestIT {

    @Autowired
    UserProxy userProxy;
    @Autowired
    GpsUtilProxy gpsUtilProxy;
    @Autowired
    RewardCentralProxy rewardCentralProxy;
    @Autowired
    TripPricerProxy tripPricerProxy;
    @Autowired
    RewardService rewardService;

	@Test
	public void userGetRewards() {
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);
		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		userDto.addToVisitedLocations(new VisitedLocation(userDto.getUserId(), new Location(attraction.getLongitude(),attraction.getLatitude()), new Date()));
		UserRewardDto userRewardDto = new UserRewardDto(userDto.getLastVisitedLocation(),attraction, rewardCentralProxy.getRewardPoints(attraction, userDto));

		userDto.addUserReward(userRewardDto);
		List<UserRewardDto> userRewardsDto = userDto.getUserRewards();
		tourGuideService.trackerService.stopTracking();

		assertEquals(userDto.getUserRewards().size(), userRewardsDto.size());
	}

	@Test
	public void isWithinAttractionProximity() {
		UserDto user = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		VisitedLocation userLocation = gpsUtilProxy.getUserLocation(user.getUserId());

		assertTrue(rewardService.isWithinAttractionProximity(attraction, userLocation.getLocation()));
	}

	@Test
	public void nearAllAttractions() {
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(userProxy,gpsUtilProxy, rewardCentralProxy, tripPricerProxy);

		rewardService.calculateRewards(tourGuideService.getUsers().get(0));
		List<UserRewardDto> userRewardsDto = tourGuideService.getRewards(tourGuideService.getUsers().get(0).getUserName());
		tourGuideService.trackerService.stopTracking();

		UserDto userDto = tourGuideService.getUsers().get(0);
		assertEquals(userDto.getUserRewards().size(), userRewardsDto.size());
	}
}