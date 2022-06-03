package User.controller;

import User.model.User;
import User.service.UserService;
import com.dto.UserLocationDto;
import com.model.Location;
import com.model.VisitedLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    Date LocTimestamp = new Date(System.currentTimeMillis());
    Location location = new Location(-117.922008D,33.817595D);
    User user = new User(UUID.randomUUID(), "BobLazar", "000", "BobLazar@tourGuide.com", LocTimestamp);
    UserLocationDto userLocation= new UserLocationDto(user.getUserId(), location);
    List<UserLocationDto> userLocationsList= new ArrayList<>();

    @Test
    void getUsersTest() throws Exception {
        Mockito.when(userService.getUsers()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/getUsers")).andExpect(status().isOk());
    }

    @Test
    void getUserTest() throws Exception {
        String username = user.getUserName();
        Mockito.when(userService.getUser(username)).thenReturn(user);
        mockMvc.perform(get("/getUser").param("userName", username))
                .andExpectAll(status().isOk());
    }

    @Test
    void getUserByIdTest() throws Exception {
        UUID userId = user.getUserId();
        Mockito.when(userService.getUserById(userId)).thenReturn(user);
        mockMvc.perform(get("/getUserById").param("userId", String.valueOf(userId)))
                .andExpectAll(status().isOk());
    }

    @Test
    void getAllCurrentLocationTest() throws Exception {
        user.addToVisitedLocations(new VisitedLocation(user.getUserId(), location,LocTimestamp));
        userLocationsList.add(userLocation);
        Mockito.when(userService.getAllCurrentLocations()).thenReturn(userLocationsList);
        mockMvc.perform(get("/getAllCurrentLocations")).andExpect(status().isOk());
    }
}
