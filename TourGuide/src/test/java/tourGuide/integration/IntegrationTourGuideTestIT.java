//package tourGuide.integration;
//
//import com.dto.NearbyAttractionsDto;
//import com.dto.UserDto;
//import com.helper.InternalTestDataSet;
//import com.helper.InternalTestHelper;
//import com.model.Provider;
//import com.model.VisitedLocation;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import tourGuide.proxy.GpsUtilProxy;
//import tourGuide.proxy.RewardCentralProxy;
//import tourGuide.proxy.TripPricerProxy;
//import tourGuide.service.TourGuideService;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//public class IntegrationTourGuideTestIT {
//
//	private GpsUtilProxy gpsUtil;
//	private RewardCentralProxy rewardCentral;
//	private TripPricerProxy tripPricer;
//
//
//	@Test
//	public void getUserLocation() throws NumberFormatException {
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(userDto);
//		tourGuideService.trackerService.stopTracking();
//		assertEquals(visitedLocation.getUserId(), userDto.getUserId());
//	}
//
//	@Test
//	public void addUser() {
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		UserDto userDto2 = new UserDto(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
//
//		tourGuideService.addUser(userDto);
//		tourGuideService.addUser(userDto2);
//
//		UserDto retriedUserDto = tourGuideService.getUser(userDto.getUserName());
//		UserDto retriedUser2Dto = tourGuideService.getUser(userDto2.getUserName());
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertEquals(userDto, retriedUserDto);
//		assertEquals(userDto2, retriedUser2Dto);
//	}
//
//	@Test
//	public void getAllUsers() {
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		UserDto userDto2 = new UserDto(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
//
//		tourGuideService.addUser(userDto);
//		tourGuideService.addUser(userDto2);
//
//		List<UserDto> allUsersDto = tourGuideService.getAllUsers();
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertTrue(allUsersDto.contains(userDto));
//		assertTrue(allUsersDto.contains(userDto2));
//	}
//
//	@Test
//	public void trackUser() {
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		VisitedLocation visitedLocation;
//		visitedLocation = tourGuideService.getUserLocation(userDto);
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertEquals(userDto.getUserId(), visitedLocation.getUserId());
//	}
//
//	@Test
//	public void getNearbyAttractions() {
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		VisitedLocation visitedLocation;
//		visitedLocation = tourGuideService.trackUserLocation(userDto);
//
//		List<NearbyAttractionsDto> attractions = tourGuideService.getNearByAttractions(visitedLocation);
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertTrue(attractions.size() > 0);
//	}
//
//	public void getTripDeals() {
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//
//		List<Provider> providers = tourGuideService.getTripDeals(userDto);
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertEquals(10, providers.size());
//	}
//}