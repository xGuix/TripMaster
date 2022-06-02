package tourGuide.integration;

import com.dto.NearbyAttractionsDto;
import com.dto.UserDto;
import com.model.Provider;
import com.model.VisitedLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import tourGuide.proxy.UserProxy;
import tourGuide.service.TourGuideService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class IntegrationTourGuideTestIT {

//    @Autowired
//    UserProxy userProxy;
//    @Autowired
//    GpsUtilProxy gpsUtil;
//    @Autowired
//    RewardCentralProxy rewardCentral;
//    @Autowired
//    TripPricerProxy tripPricer;
//
//	@Test
//	public void getUserLocation() throws NumberFormatException {
//		userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(userDto);
//		tourGuideService.trackerService.stopTracking();
//		assertEquals(visitedLocation.getUserId(), userDto.getUserId());
//	}
//
//	@Test
//	public void addUser() {
//        userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		UserDto userDto2 = new UserDto(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
//
//		userProxy.addUser(userDto);
//		userProxy.addUser(userDto2);
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
//        userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		UserDto userDto2 = new UserDto(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
//
//		userProxy.addUser(userDto);
//		userProxy.addUser(userDto2);
//
//		List<UserDto> allUsersDto = tourGuideService.getUsers();
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertTrue(allUsersDto.contains(userDto));
//		assertTrue(allUsersDto.contains(userDto2));
//	}
//
//	@Test
//	public void trackUser() {
//        userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
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
//        userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		VisitedLocation visitedLocation;
//		visitedLocation = tourGuideService.trackUserLocation(userDto);
//
//		List<NearbyAttractionsDto> attractions = tourGuideService.getNearbyAttractions(visitedLocation);
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertTrue(attractions.size() > 0);
//	}
//
//	public void getTripDeals() {
//        userProxy.getUser("InternalUser0");
//		TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
//
//		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//
//		List<Provider> providers = tourGuideService.getTripDeals(userDto);
//
//		tourGuideService.trackerService.stopTracking();
//
//		assertEquals(10, providers.size());
//	}
}