package tourGuide.proxy;

import com.model.Attraction;
import com.model.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "gpsUtil", url = "localhost:8282")
public interface GpsUtilProxy {

    @GetMapping(value = "/attractions")
    List<Attraction> getAttractions();

    @GetMapping(value = "/userLocation")
    VisitedLocation getUserLocation (@RequestParam("userId") UUID userId);
}