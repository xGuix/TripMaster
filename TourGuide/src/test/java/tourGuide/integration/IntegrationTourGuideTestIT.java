package tourGuide.integration;

import com.dto.NearbyAttractionsDto;
import com.dto.UserDto;
import com.model.Attraction;
import com.model.Location;
import com.model.Provider;
import com.model.VisitedLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import tourGuide.proxy.UserProxy;
import tourGuide.service.TourGuideService;
import tourGuide.util.InternalTestDataSet;
import tourGuide.util.InternalTestHelper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class IntegrationTourGuideTestIT {

    @Autowired
    UserProxy userProxy;
    @Autowired
    GpsUtilProxy gpsUtilProxy;
    @Autowired
    RewardCentralProxy rewardCentralProxy;
    @Autowired
    TripPricerProxy tripPricerProxy;

	@Test
	public void getUserLocation() throws NumberFormatException {
		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);
		VisitedLocation visitedLocation = tourGuideService.getUserLocation(userDto.getUserId());
		tourGuideService.trackerService.stopTracking();

		assertEquals(visitedLocation.getUserId(), userDto.getUserId());
	}

	@Test
	public void addUser() {
		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		UserDto userDto2 = new UserDto(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(2);
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);
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
		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		UserDto userDto2 = new UserDto(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(2);
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);
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
		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);

		VisitedLocation visitedLocation = tourGuideService.getUserLocation(userDto.getUserId());
		tourGuideService.trackerService.stopTracking();

		assertEquals(userDto.getUserId(), visitedLocation.getUserId());
	}

	@Test
	public void getNearbyAttractions() {
		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);
		Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		userDto.addToVisitedLocations(new VisitedLocation(userDto.getUserId(), new Location(attraction.getLongitude(),attraction.getLatitude()), new Date()));

		List<NearbyAttractionsDto> attractionsList = tourGuideService.getNearbyAttractions(userDto.getUserId());
		tourGuideService.trackerService.stopTracking();

		assertTrue(attractionsList.size() > 0);
	}

    @Test
	public void getTripDeals() {
		UserDto userDto = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);

		List<Provider> providers = tourGuideService.getTripDeals(userDto);
		tourGuideService.trackerService.stopTracking();

		assertEquals(5, providers.size());
	}
}