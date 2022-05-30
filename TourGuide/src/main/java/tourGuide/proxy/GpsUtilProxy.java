package tourGuide.proxy;

import com.model.Attraction;
import com.model.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value="gpsUtil", url="localhost:8282")
public interface GpsUtilProxy {

    /**
     * Get users location.
     * @return User visited location
     */
    @RequestMapping(value = "/userLocation")
    VisitedLocation getUserLocation (@RequestParam("userId") UUID userId);

    /**
     * Get attraction list.
     * @return attractionsList List of all attraction
     */
    @RequestMapping(value = "/getAttractions")
    List<Attraction> getAttractions();
}