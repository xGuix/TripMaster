package User.service;

import User.model.User;
import User.model.UserRewardDto;
import com.dto.UserLocationDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    User user;
    Date LocTimestamp;
    Location location;
    UserRewardDto userReward;
    Attraction attraction;
    UUID attractionId;


    @BeforeEach
    void setTest(){

        user = new User(UUID.fromString("908ea1ae-d433-40f9-b575-fe448906a200"), "BobLazar", "000", "BobLazar@tourGuide.com", LocTimestamp);
        LocTimestamp = new Date(System.currentTimeMillis());
        location = new Location(-117.922008D,33.817595D);
        attraction = new Attraction("Disneyland", "Anaheim", "CA",attractionId, 33.817595D, -117.922008D);
        userReward = new UserRewardDto(new VisitedLocation(user.getUserId(),location, LocTimestamp),new Attraction(),100);
        user.addUserReward(userReward);
        user.addToVisitedLocations(userReward.getVisitedLocation());
        userService.internalUserMap.put("BobLazar", user);
    }

    @Test
    void getAllUsers() {
        List<User> result = userService.getUsers();
        assertNotNull(result);
    }


    @Test
    void getUserTest() {
        User result = userService.getUser(user.getUserName());
        assertEquals(user, result);
    }

    @Test
    void getUserRewardTest() {
        List<UserRewardDto> result = userService.getUserRewards(user);
        assertEquals(user.getUserRewards(), result);
    }

    @Test
    void getAllCurrentLocationTest() {
        List<UserLocationDto> result = userService.getAllCurrentLocations();
        assertNotNull(result);
    }
}
