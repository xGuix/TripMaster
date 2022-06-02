package User;

import User.model.User;
import User.model.UserPreferences;
import User.service.UserService;
import com.dto.UserLocationDto;
import com.dto.UserRewardDto;
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
    private UserService userService;

    /**
     * Gets user test.
     */
    @Test
    void getUserTest() {
        User user1 = new User(UUID.randomUUID(), "jon");
        User user2 = new User(UUID.randomUUID(), "jon2");

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
        assertNotEquals(0, users.size());
    }

    /**
     * Add user rewards.
     */
    @Test
    void addUserRewards(){
        User user = userService.getUsers().get(0);
        long now = new Date().getTime();
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
        Attraction attraction = new Attraction("name", "city", "stat", UUID.randomUUID(),1234567, 12345678);
        UserRewardDto userReward = new UserRewardDto(visitedLocation, attraction, 325);
        userService.addUserReward(user.getUserName(), userReward);

        assertNotNull(user.getUserRewards());
        assertNotEquals(0, user.getUserRewards().size());
    }

    /**
     * Get user rewards test.
     */
    @Test
    void getUserRewardsTest(){
        User user = userService.getUsers().get(0);
        long now = new Date().getTime();
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
        Attraction attraction = new Attraction("name", "city", "stat",UUID.randomUUID(), 1234567, 12345678);
        UserRewardDto userReward = new UserRewardDto(visitedLocation, attraction, 325);
        user.addUserReward(userReward);
        List<UserRewardDto> userRewards = userService.getUserRewards(user);

        assertNotNull(userRewards);
        assertNotEquals(0, userRewards.size());
        assertNotNull(userRewards.get(0).getAttraction());
    }

    /**
     * Get all current locations test.
     */
    @Test
    void getAllCurrentLocationsTest(){
        List<UserLocationDto> usersLocations = userService.getAllCurrentLocations();
        assertNotNull(usersLocations);
        assertNotEquals(0, usersLocations.size());
        assertNotNull(usersLocations.get(0).getUserId());
    }

    /**
     * Update trip deals test.
     */
    @Test
    void updateTripDealsTest(){
        User user = userService.getUsers().get(0);
        Provider provider = new Provider("name", 200, UUID.randomUUID());
        Provider provider2 = new Provider("nameTest", 300,UUID.randomUUID());
        List<Provider> providerList = new ArrayList<>();
        providerList.add(0,provider);
        providerList.add(1,provider2);

        userService.updateTripDeals(user.getUserName(), providerList);

        assertNotNull(user.getTripDeals());
        assertNotEquals(0, userService.getUser(user.getUserName()).getTripDeals().size());
        assertNotNull(user.getTripDeals().get(0).getTripId());

    }

    /**
     * Create visited location test.
     */
    @Test
    void createVisitedLocationTest(){
        //given
        User user = userService.getUsers().get(0);
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(1234567, 1234567), new Date());
        int visitedLocationNumber = user.getVisitedLocations().size();

        //when
        userService.addVisitedLocation(user.getUserName(),visitedLocation);
        //then
        assertEquals(visitedLocationNumber + 1, userService.getUsers().get(0).getVisitedLocations().size());
    }

    @Test
    void updateUserPreferencesTest(){
        User user = userService.getUsers().get(0);
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setNumberOfAdults(3);
        userPreferences.setNumberOfChildren(2);
        userService.UpdateUserPreferences(user.getUserName(), userPreferences);

        assertEquals(user.getUserPreferences().getNumberOfAdults(), 3);
        assertEquals(user.getUserPreferences().getNumberOfChildren(), 2);
    }
}
