package tourGuide.integration;

import com.dto.NearbyAttractionsDto;
import com.dto.UserDto;
import com.model.Provider;
import com.model.VisitedLocation;
import com.util.InternalTestHelper;
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

    @Autowired
    UserProxy userProxy;
    @Autowired
    GpsUtilProxy gpsUtil;
    @Autowired
    RewardCentralProxy rewardCentral;
    @Autowired
    TripPricerProxy tripPricer;

    UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    UserDto userDto2 = new UserDto(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

	@Test
	public void getUserLocation() throws NumberFormatException {
		InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
		VisitedLocation visitedLocation = tourGuideService.getUserLocation(userDto.getUserId());
		tourGuideService.trackerService.stopTracking();

		assertEquals(visitedLocation.getUserId(), userDto.getUserId());
	}

	@Test
	public void addUser() {
		InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
		userProxy.addUser(userDto);
        userProxy.addUser(userDto2);

		UserDto result = tourGuideService.getUser(userDto.getUserName());
        UserDto result2 = tourGuideService.getUser(userDto2.getUserName());
		tourGuideService.trackerService.stopTracking();

        assertEquals(userDto.getUserName(), result.getUserName());
        assertEquals(userDto.getEmailAddress(), result.getEmailAddress());
        assertEquals(userDto2.getUserName(), result2.getUserName());
        assertEquals(userDto2.getEmailAddress(), result2.getEmailAddress());
	}

	@Test
	public void getAllUsers() {
		InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
        userProxy.addUser(userDto);
        userProxy.addUser(userDto2);

        List<UserDto> allUsers = tourGuideService.getUsers();
        allUsers.add(userDto);
        allUsers.add(userDto2);
		tourGuideService.trackerService.stopTracking();

		assertTrue(allUsers.contains(userDto));
		assertTrue(allUsers.contains(userDto2));
	}

	@Test
	public void trackUser() {
		InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(userDto);
		tourGuideService.trackerService.stopTracking();

		assertEquals(userDto.getUserId(), visitedLocation.getUserId());
	}

	@Test
	public void getNearbyAttractions() {
		InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
		List<NearbyAttractionsDto> attractions = tourGuideService.getNearbyAttractions(userDto);

		assertTrue(attractions.size() > 0);
	}

    @Test
	public void getTripDeals() {
		InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(userProxy, gpsUtil, rewardCentral, tripPricer);
		List<Provider> providers = tourGuideService.getTripDeals(userDto);
		tourGuideService.trackerService.stopTracking();

		assertEquals(5, providers.size());
	}
}