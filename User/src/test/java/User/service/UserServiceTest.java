package User.service;

import User.model.User;
import com.dto.UserRewardDto;
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
    UserRewardDto userRewardDto;

    @BeforeEach
    void setTest(){

        user = new User(UUID.randomUUID(), "BobLazar", "000", "BobLazar@tourGuide.com", LocTimestamp);
        LocTimestamp = new Date(System.currentTimeMillis());
        location = new Location(-117.922008D,33.817595D);
        userRewardDto = new UserRewardDto(new VisitedLocation(user.getUserId(),location, LocTimestamp),new Attraction(),100);
        user.addUserReward(userRewardDto);
        user.addToVisitedLocations(userRewardDto.getVisitedLocation());
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
    void getUserByIdTest() {
        User result = userService.getUserById(user.getUserId());
        assertEquals(user, result);
    }

    @Test
    void getUserRewardTest() {
        List<UserRewardDto> result = userService.getUserRewards(user);
        assertEquals(user.getUserRewards(), result);
    }
}
