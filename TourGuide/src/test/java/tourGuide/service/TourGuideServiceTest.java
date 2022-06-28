package tourGuide.service;

import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import tourGuide.proxy.UserProxy;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TourGuideServiceTest {

    @Autowired
    TourGuideService tourGuideService;

    @MockBean
    UserProxy userProxy;

    @MockBean
    GpsUtilProxy gpsUtilProxy;

    @MockBean
    RewardCentralProxy rewardCentralProxy;

    @MockBean
    TripPricerProxy tripPricerProxy;

    UserDto user;
    List<UserLocationDto> userLocList;

    @BeforeEach
    void setupTest(){
        user= new UserDto(UUID.fromString("648ed5ea-b766-4aee-a0b7-3686e166c977"),"username","000","test@test.com");
        userLocList = new ArrayList<>();
    }

    @Test
    void getUserRewardsTest() {
        user.addUserReward(new UserRewardDto(new VisitedLocation(), new Attraction(), 100));
        Mockito.when(userProxy.getUser(user.getUserName())).thenReturn(user);
        assertNotNull(user.getUserRewards());
    }

    @Test
    void getAllUsersWithLocationTest() {
        Mockito.when(userProxy.getAllCurrentLocations()).thenReturn(userLocList);
        tourGuideService.getAllCurrentLocations();
        Mockito.verify(userProxy,Mockito.times(1)).getAllCurrentLocations();
    }

    @Test
    void trackUserLocationTest() throws URISyntaxException {
        CompletableFuture<?> result;
        result = tourGuideService.trackUserLocation(user.getUserId());
        assertNotNull(result);
    }

    @Test
    void getNearByAttractionsTest() throws URISyntaxException {
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(),new Location(10,10),null);
        Mockito.when(userProxy.getUser(user.getUserName())).thenReturn(user);
        Mockito.when(gpsUtilProxy.getUserLocation(user.getUserId())).thenReturn(visitedLocation);
        tourGuideService.getNearbyAttractions(user.getUserId());
        //Then
        Mockito.verify(gpsUtilProxy,Mockito.times(1)).getUserLocation(user.getUserId());
    }

    @Test
    void getUserTest() {
        UserDto expected = user;
        UserDto result;
        Mockito.when(userProxy.getUser(user.getUserName())).thenReturn(user);
        result = tourGuideService.getUser(user.getUserName());
        assertEquals(expected,result);
    }
}
