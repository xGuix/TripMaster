package TripPricer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TripPricerServiceTest {

    @Autowired
    TripPricerService tripPricerService;

    String tripPricerApiKey = "test-server-api-key";
    UUID uuid = UUID.randomUUID();
    int adults = 1;
    int children = 0;
    int nightsStay = 7;
    int rewardsPoints = 10;

    @Test
    void getTripDealsCallTest() {
        List<Provider> result = tripPricerService.getTripDeals(tripPricerApiKey,uuid,adults,children,nightsStay,rewardsPoints);
        assertNotNull(result);
    }
}