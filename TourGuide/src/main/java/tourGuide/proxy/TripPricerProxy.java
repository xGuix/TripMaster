package tourGuide.proxy;

import com.model.Provider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "tripPricer", url="localhost:8484" /*url="${tourguide.microservice-trippricer}"*/)
public interface TripPricerProxy {

    /**
     * Get price provider.
     * @param apiKey String ApiKey
     * @param attractionId UUID Id attraction
     * @param adults int adult
     * @param children int children
     * @param nightsStay int nightsStay
     * @param rewardsPoints int rewardPoints
     * @return Providers list
     */
    @RequestMapping("/getTripDeals")
    List<Provider> getTripDeals(@RequestParam("apiKey") String apiKey,
                            @RequestParam("attractionId") UUID attractionId,
                            @RequestParam("adults") int adults,
                            @RequestParam("children") int children,
                            @RequestParam("nightsStay") int nightsStay,
                            @RequestParam("rewardsPoints") int rewardsPoints );
}