package User;

import User.model.User;
import User.service.UserService;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserIntegrationTestIT {

    @Autowired
    UserService userService;

    User user1 = new User(UUID.randomUUID(), "jon");
    User user2 = new User(UUID.randomUUID(), "jon2");

    /**
     * Gets user test.
     */
    @Test
    void getUserTest() {

        userService.addUser(user1);
        userService.addUser(user2);

        List<User> allUsers = userService.getUsers();

        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
        assertNotNull(user1);
        assertNotNull(user1.getUserId());
    }

    /**
     * Get all users test.
     */
    @Test
    void getAllUsersTest(){
        List<User> users = userService.getUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
    }

    /**
     * Add user rewards.
     */
    @Test
    void addUserRewards(){
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
        Attraction attraction = new Attraction("Lala land", "city", "state", UUID.randomUUID(),1234567, 12345678);
        UserRewardDto userReward = new UserRewardDto(visitedLocation, attraction, 325);
        userService.getUserRewards(user1).add(userReward);

        assertNotNull(user1.getUserRewards());
        assertNotEquals(0, user1.getUserRewards().size());
    }

    /**
     * Get user rewards test.
     */
    @Test
    void getUserRewardsTest(){
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
        Attraction attraction = new Attraction("Lala land", "city", "state",UUID.randomUUID(), 1234567, 12345678);
        UserRewardDto userReward = new UserRewardDto(visitedLocation, attraction, 325);
        user1.addUserReward(userReward);
        List<UserRewardDto> userRewards = userService.getUserRewards(user1);

        assertNotNull(userRewards);
        assertNotEquals(0, userRewards.size());
        assertNotNull(userRewards.get(0).getAttraction());
    }

//    /**
//     * Update trip deals test.
//     */
//    @Test
//    void updateTripDealsTest(){
//        Provider provider = new Provider("name", 200, UUID.randomUUID());
//        Provider provider2 = new Provider("nameTest", 300,UUID.randomUUID());
//        List<Provider> providerList = new ArrayList<>();
//        providerList.add(0,provider);
//        providerList.add(1,provider2);
//
//        user1.getTripDeals().addAll(0, providerList);
//        userService.updateTripDeals(user1.getUserName(), providerList);
//
//        assertNotNull(user1.getTripDeals());
//        assertNotEquals(0, userService.getUser(user1.getUserName()).getTripDeals().size());
//        assertNotNull(user1.getTripDeals().get(0).getTripId());
//
//    }
//
//    /**
//     * Create visited location test.
//     */
//    @Test
//    void createVisitedLocationTest(){
//        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
//        int visitedLocationNumber = user1.getVisitedLocations().size();
//        userService.addVisitedLocation(user1.getUserName(),visitedLocation);
//
//        assertEquals(visitedLocationNumber + 1, userService.getUser(user1.getUserName()).getVisitedLocations().size());
//    }
//
//    @Test
//    void updateUserPreferencesTest(){
//        UserPreferences userPreferences = new UserPreferences();
//        userPreferences.setNumberOfAdults(3);
//        userPreferences.setNumberOfChildren(2);
//        userService.UpdateUserPreferences(user1.getUserName(), userPreferences);
//
//        assertEquals(user1.getUserPreferences().getNumberOfAdults(), 3);
//        assertEquals(user1.getUserPreferences().getNumberOfChildren(), 2);
//    }
}
