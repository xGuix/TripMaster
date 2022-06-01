package tourGuide.proxy;

import com.model.Provider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "tripPricer", url = "localhost:8484")
public interface TripPricerProxy {

    /**
     * Get price provider.
     * @return Providers list
     */
    @RequestMapping("/getPrice")
    List<Provider> getTripDeals(@RequestParam("apiKey") String apiKey,
                            @RequestParam("attractionId") UUID attractionId,
                            @RequestParam("adults") int adults,
                            @RequestParam("children") int children,
                            @RequestParam("nightsStay") int nightsStay,
                            @RequestParam("rewardsPoints") int rewardsPoints );
}