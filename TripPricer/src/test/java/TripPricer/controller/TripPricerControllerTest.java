package TripPricer.controller;

import TripPricer.service.TripPricerService;
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
@WebMvcTest(TripPricerController.class)
public class TripPricerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TripPricerService tripPricerService;

    List<tripPricer.Provider> providerList = new ArrayList<>();
    String tripPricerApiKey = "test-server-api-key";
    UUID uuid = UUID.randomUUID();
    int adults = 1;
    int children = 0;
    int nightsStay = 7;
    int rewardsPoints = 10;

    @Test
    void getAllAttractionsTest() throws Exception {

        Mockito.when(tripPricerService.getTripDeals(tripPricerApiKey,uuid,adults,children,nightsStay,rewardsPoints)).thenReturn(providerList);
        mockMvc.perform(get("/getTripDeals")
                        .param("apiKey", tripPricerApiKey)
                        .param("attractionId", String.valueOf(uuid))
                        .param("adults", String.valueOf(adults))
                        .param("children", String.valueOf(children))
                        .param("nightsStay", String.valueOf(nightsStay))
                        .param("rewardsPoints", String.valueOf(rewardsPoints)))
                .andExpect(status().isOk());
    }
}