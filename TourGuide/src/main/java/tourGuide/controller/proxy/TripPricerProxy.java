package tourGuide.controller.proxy;

import com.model.Provider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "tripPricer", url = "localhost:8484")
public interface TripPricerProxy {

    @RequestMapping("/getPrice")
    List<Provider> getPrice(@RequestParam("apiKey") String apiKey,
                            @RequestParam("attractionId") String attractionId,
                            @RequestParam("adults") int adults,
                            @RequestParam("children") int children,
                            @RequestParam("nightsStay") int nightsStay,
                            @RequestParam("rewardsPoints") int rewardsPoints );
}