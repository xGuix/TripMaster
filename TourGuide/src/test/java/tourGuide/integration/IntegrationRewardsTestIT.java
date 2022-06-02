package tourGuide.integration;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
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

//    @Autowired
//    UserProxy userProxy;
//    @Autowired
//    GpsUtilProxy gpsUtil;
//    @Autowired
//    RewardCentralProxy rewardCentral;
//    @Autowired
//    TripPricerProxy tripPricer;
//    @Autowired
//    RewardService rewardService;
//
//	@Test
//	public void userGetRewards() {
//        userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		Attraction attraction = gpsUtil.getAttractions().get(0);
//		userDto.addToVisitedLocations(new VisitedLocation(userDto.getUserId(), new Location(attraction.getLongitude(),attraction.getLatitude()), new Date()));
//		UserRewardDto userRewardDto = new UserRewardDto(userDto.getLastVisitedLocation(),attraction, rewardCentral.getRewardPoints(attraction, userDto));
//		userDto.addUserReward(userRewardDto);
//		List<UserRewardDto> userRewardsDto = userDto.getUserRewards();
//		tourGuideService.trackerService.stopTracking();
//		assertEquals(userDto.getUserRewards().size(), userRewardsDto.size());
//	}
//
//	@Test
//	public void isWithinAttractionProximity() {
//		Attraction attraction = gpsUtil.getAttractions().get(0);
//		assertTrue(rewardCentral.isWithinAttractionProximity(attraction, new Location(attraction.getLongitude(),attraction.getLatitude())));
//	}
//
//	@Test
//	public void nearAllAttractions() {
//        userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy,gpsUtil, rewardCentral, tripPricer);
//
//        rewardService.calculateRewards(tourGuideService.getUsers().get(0));
//		List<UserRewardDto> userRewardsDto = tourGuideService.getRewards(tourGuideService.getUsers().get(0).getUserName());
//		tourGuideService.trackerService.stopTracking();
//
//		UserDto userDto = tourGuideService.getUsers().get(0);
//		assertEquals(userDto.getUserRewards().size(), userRewardsDto.size());
//	}
}