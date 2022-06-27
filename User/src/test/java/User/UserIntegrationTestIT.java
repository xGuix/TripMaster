package User;

import User.model.User;
import User.model.UserPreferences;
import User.model.UserReward;
import User.service.UserService;
import com.model.Attraction;
import com.model.Location;
import com.model.Provider;
import com.model.VisitedLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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
     * Get all users test.
     */
    @Test
    void getAllUsersTest(){
        userService.addUser(user1);
        userService.addUser(user2);

        List<User> allUsers = userService.getUsers();

        assertNotNull(allUsers);
        assertNotEquals(0, allUsers.size());
    }

    /**
     * Gets user test.
     */
    @Test
    void getUserTest() {
        userService.addUser(user2);

        User user = userService.getUser(user2.getUserName());

        assertNotNull(user);
        assertEquals(user2.getUserName(), user.getUserName());
    }


    /**
     * Add user rewards.
     */
    @Test
    void addUserRewards(){
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
        Attraction attraction = new Attraction("Lala land", "city", "state", UUID.randomUUID(),1234567, 12345678);
        UserReward userReward = new UserReward(visitedLocation, attraction, 325);
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
        UserReward userReward = new UserReward(visitedLocation, attraction, 325);
        user1.addUserReward(userReward);
        List<UserReward> userRewards = userService.getUserRewards(user1);

        assertNotNull(userRewards);
        assertNotEquals(0, userRewards.size());
        assertNotNull(userRewards.get(0).getAttraction());
    }

    /**
     * Update trip deals test.
     */
    @Test
    void updateTripDealsTest(){
        Provider provider = new Provider("name", 200, UUID.randomUUID());
        Provider provider2 = new Provider("nameTest", 300,UUID.randomUUID());
        List<Provider> providerList = new ArrayList<>();

        providerList.add(0,provider);
        providerList.add(1,provider2);
        user1.setTripDeals(providerList);

        assertNotNull(user1.getTripDeals());
        assertNotEquals(0, user1.getTripDeals().size());
        assertNotNull(user1.getTripDeals().get(0).getTripId());

    }

    /**
     * Create visited location test.
     */
    @Test
    void createVisitedLocationTest(){
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
        int visitedLocationNumber = user1.getVisitedLocations().size();
        userService.addVisitedLocation(user1.getUserName(),visitedLocation);

        assertEquals(visitedLocationNumber + 1, userService.getUser(user1.getUserName()).getVisitedLocations().size());
    }

    @Test
    void updateUserPreferencesTest(){
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setNumberOfAdults(3);
        userPreferences.setNumberOfChildren(2);
        user1.setUserPreferences(userPreferences);
        userService.UpdateUserPreferences(user1.getUserName(), userPreferences);

        assertEquals(3, user1.getUserPreferences().getNumberOfAdults());
        assertEquals(2, user1.getUserPreferences().getNumberOfChildren());
    }
}
