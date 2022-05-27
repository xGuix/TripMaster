//package tourGuide.integration;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import com.model.Attraction;
//import com.model.Location;
//import com.model.VisitedLocation;
//import com.dto.UserDto;
//import com.dto.UserRewardDto;
//import com.helper.InternalTestHelper;
//import com.helper.InternalTestDataSet;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import tourGuide.proxy.GpsUtilProxy;
//import tourGuide.proxy.RewardCentralProxy;
//import tourGuide.proxy.TripPricerProxy;
//import tourGuide.service.TourGuideService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//public class IntegrationRewardsTestIT {
//
//	@Test
//	public void userGetRewards() {
//		GpsUtilProxy gpsUtil;
//		RewardCentralProxy rewardCentral;
//		TripPricerProxy tripPricer;
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		Attraction attraction = gpsUtil.getAttractions().get(0);
//		userDto.addToVisitedLocations(new VisitedLocation(userDto.getUserId(), new Location(attraction.getLongitude(),attraction.getLatitude()), new Date()));
//		UserRewardDto userRewardDto = new UserRewardDto(userDto.getLastVisitedLocation(),attraction, rewardCentral.getRewardPoints(attraction, userDto.getUserName()));
//		userDto.addUserReward(userRewardDto);
//		List<UserRewardDto> userRewardsDto = userDto.getUserRewards();
//		tourGuideService.trackerService.stopTracking();
//		assertEquals(userDto.getUserRewards().size(), userRewardsDto.size());
//	}
//
//	@Test
//	public void isWithinAttractionProximity() {
//		GpsUtilProxy gpsUtil;
//		RewardCentralProxy rewardCentral;
//		Attraction attraction = gpsUtil.getAttractions().get(0);
//		assertTrue(rewardCentral.isWithinAttractionProximity(attraction, new Location(attraction.getLongitude(),attraction.getLatitude())));
//	}
//
//	@Test
//	public void nearAllAttractions() {
//		GpsUtilProxy gpsUtil;
//		RewardCentralProxy rewardCentral;
//		TripPricerProxy tripPricer;
//		rewardCentral.setProximityBuffer(Integer.MAX_VALUE);
//
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,gpsUtil, rewardCentral, tripPricer);
//
//		rewardCentral.calculateRewards(tourGuideService.getAllUsers().get(0));
//		List<UserRewardDto> userRewardsDto = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
//		tourGuideService.trackerService.stopTracking();
//
//		UserDto userDto = tourGuideService.getAllUsers().get(0);
//		assertEquals(userDto.getUserRewards().size(), userRewardsDto.size());
//	}
//}