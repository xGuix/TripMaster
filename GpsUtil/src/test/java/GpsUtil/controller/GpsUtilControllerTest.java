package GpsUtil.controller;

import GpsUtil.service.GpsUtilService;
import com.model.Location;
import gpsUtil.location.Attraction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GpsUtilController.class)
public class GpsUtilControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GpsUtilService gpsUtilService;

    List<gpsUtil.location.Attraction> attractionList = new ArrayList<>();
    Location location = new Location(-117.922008D,33.817595D);


    @Test
    void getAttractionsTest() throws Exception {
        attractionList.add(new Attraction("Disneyland","City","State",location.getLatitude(), location.getLongitude()));
        Mockito.when(gpsUtilService.getAllAttractions()).thenReturn(attractionList);
        mockMvc.perform(get("/getAttractions"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserLocationTest() throws Exception {
        mockMvc.perform(get("/userLocation")
                .param("userId",String.valueOf(UUID.randomUUID())))
                .andExpect(status().isOk());
    }
}
