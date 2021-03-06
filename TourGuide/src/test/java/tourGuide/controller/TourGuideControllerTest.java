package tourGuide.controller;

import com.dto.UserDto;
import com.model.Location;
import com.model.VisitedLocation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tourGuide.service.TourGuideService;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TourGuideControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TourGuideService tourGuideService;

    @Test
    public void indexTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void getLocation() throws Exception {
        Location location = new Location(33.817595D,-117.922008D);
        Date date = new Date();
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), location, date);
        Mockito.when(tourGuideService.getUserLocation(visitedLocation.getUserId())).thenReturn(visitedLocation);

        mockMvc.perform(get("/getLocation").param("userId", String.valueOf(visitedLocation.getUserId())))
                .andExpect(status().isOk());
    }

    @Test
    public void getNearbyAttractionsTest() throws Exception {
        String userName = "username";
        UserDto userDto = new UserDto(UUID.randomUUID(), userName);
        Mockito.when(tourGuideService.getUser(userDto.getUserName())).thenReturn(userDto);

        mockMvc.perform(get("/getNearbyAttractions").param("userName", userName))
                .andExpect(status().isOk());
    }

    @Test
    public void getRewardsTest() throws Exception {
        String userName = "username";
        Mockito.when(tourGuideService.getRewards(userName)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/getRewards").param("userName", userName))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCurrentLocationsTest() throws Exception {
        Mockito.when(tourGuideService.getUsers()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/getAllCurrentLocations"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTripDealsTest() throws Exception {
        String userName = "username";
        Mockito.when(tourGuideService.getTripDeals(tourGuideService.getUser(userName))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/getTripDeals").param("userName", userName))
                .andExpect(status().isOk());
    }
}