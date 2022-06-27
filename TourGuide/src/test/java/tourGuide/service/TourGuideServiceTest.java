//package tourGuide.service;
//
//import com.dto.UserDto;
//import com.dto.UserRewardDto;
//import com.model.Attraction;
//import com.model.Location;
//import com.model.VisitedLocation;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import tourGuide.proxy.UserProxy;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class TourGuideServiceTest {
//
//    @Autowired
//    UserProxy userProxy;
//
//    @Test
//    void getUserRewardsTest() {
//        //Given
//        UserDto user = new UserDto(UUID.randomUUID(),"username","1234","a@a.be");
//        user.getUserRewards().add(new UserRewardDto(new VisitedLocation(), new Attraction(), 100));
//        user.getUserRewards().add(new UserRewardDto(new VisitedLocation(), new Attraction(), 100));
//        //When
//        Mockito.when(userProxy.getUser(user.getUserName())).thenReturn(user);
//        //Then
//        assertEquals(user.getUserRewards(),tourGuideService.getUserRewards(user.getUserName()));
//    }
//
//    @Test
//    void getAllUsersWithLocationTest() {
//        //Given
//        //When
//        Mockito.when(userWebClient.getAllUsersWithLocation()).thenReturn(new HashMap<>());
//        tourGuideService.getAllUsersWithLocation();
//        //Then
//        Mockito.verify(userWebClient,Mockito.times(1)).getAllUsersWithLocation();
//    }
//
//    @Test
//    void getTripDealsTest() throws URISyntaxException {
//        //Given
//        User user = new User(UUID.fromString("648ed5ea-b766-4aee-a0b7-3686e166c977"),"username","1234","a@a.be");
//        int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
//        //When
//        Mockito.when(userWebClient.getUser(user.getUserName())).thenReturn(user);
//        Mockito.when(pricerWebClient.generateTripDeals(
//                user.getUserId(),
//                user.getUserName(),
//                user.getUserPreferences().getNumberOfAdults(),
//                user.getUserPreferences().getNumberOfChildren(),
//                user.getUserPreferences().getTripDuration(),
//                cumulatativeRewardPoints
//        )).thenReturn(new ArrayList<>());
//        tourGuideService.getTripDeals(user.getUserName());
//        //Then
//        Mockito.verify(pricerWebClient,Mockito.times(1)).generateTripDeals( user.getUserId(),
//                user.getUserName(),
//                user.getUserPreferences().getNumberOfAdults(),
//                user.getUserPreferences().getNumberOfChildren(),
//                user.getUserPreferences().getTripDuration(),
//                cumulatativeRewardPoints);
//    }
//
//    @Test
//    void getLocationTest() {
//        //Given
//        String username = "username";
//        //When
//        Mockito.when(userWebClient.getUserLocationFromUser(username)).thenReturn(new VisitedLocation());
//        tourGuideService.getLocation(username);
//        //Then
//        Mockito.verify(userWebClient,Mockito.times(1)).getUserLocationFromUser(username);
//    }
//
//    @Test
//    void trackUserLocationTest() throws URISyntaxException {
//        //Given
//        User user = new User(UUID.fromString("648ed5ea-b766-4aee-a0b7-3686e166c977"),"username","1234","a@a.be");
//        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(),new Location(10,10),null);
//        VisitedLocation expected = visitedLocation;
//        VisitedLocation result;
//        //When
//        Mockito.when(gpsWebClient.trackUserLocationFromGps(user.getUserId())).thenReturn(visitedLocation);
//        doNothing().when(userWebClient).addUserVisitedLocation(user.getUserName(),visitedLocation);
//        result = tourGuideService.trackUserLocation(user);
//        //Then
//        Assertions.assertEquals(expected,result);
//    }
//
//    @Test
//    void calculateRewardsTest() throws URISyntaxException {
//        //Given
//        User user = new User(UUID.fromString("648ed5ea-b766-4aee-a0b7-3686e166c977"),"username","1234","a@a.be");
//        //When
//        doNothing().when(rewardsWebClient).calculateRewards(user);
//        tourGuideService.calculateRewards(user);
//        //Then
//        Mockito.verify(rewardsWebClient,Mockito.times(1)).calculateRewards(user);
//    }
//
//    @Test
//    void getNearByAttractionsTest() throws URISyntaxException {
//        //Given
//        User user = new User(UUID.fromString("648ed5ea-b766-4aee-a0b7-3686e166c977"),"username","1234","a@a.be");
//        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(),new Location(10,10),null);
//        //When
//        Mockito.when(userWebClient.getUser(user.getUserName())).thenReturn(user);
//        Mockito.when(gpsWebClient.trackUserLocationFromGps(user.getUserId())).thenReturn(visitedLocation);
//        tourGuideService.getNearByAttractions(user.getUserName());
//        //Then
//        Mockito.verify(gpsWebClient,Mockito.times(1)).getNearByAttractionsFromGps(visitedLocation.getLocation().getLatitude(),visitedLocation.getLocation().getLongitude(),5);
//    }
//
//    @Test
//    void getUserTest() {
//        //Given
//        User user = new User(UUID.randomUUID(),"username","1234","a@a.be");
//        User expected = user;
//        User result;
//        //When
//        Mockito.when(userWebClient.getUser(user.getUserName())).thenReturn(user);
//        result = tourGuideService.getUser(user.getUserName());
//        //Then
//        Assertions.assertEquals(expected,result);
//    }
//
//
//    @Test
//    void generateTripDealsTest() throws URISyntaxException {
//        //Given
//        User user = new User(UUID.fromString("f6dfa5ba-3e4b-4dab-9e1d-6dc1cbf867f7"),"username","1234","a@a.be");
//        user.getUserRewards().add(Data.getUserReward1(user.getUserId()));
//        user.getUserRewards().add(Data.getUserReward2(user.getUserId()));
//        int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
//        //When
//        Mockito.when(pricerWebClient.generateTripDeals(
//                user.getUserId(),
//                user.getUserName(),
//                user.getUserPreferences().getNumberOfAdults(),
//                user.getUserPreferences().getNumberOfChildren(),
//                user.getUserPreferences().getTripDuration(),
//                cumulatativeRewardPoints)).thenReturn(null);
//        tourGuideService.generateTripDeals(user);
//        //Then
//        Mockito.verify(pricerWebClient,Mockito.times(1)).generateTripDeals(
//                user.getUserId(),
//                user.getUserName(),
//                user.getUserPreferences().getNumberOfAdults(),
//                user.getUserPreferences().getNumberOfChildren(),
//                user.getUserPreferences().getTripDuration(),
//                cumulatativeRewardPoints);
//    }
//}
