package GpsUtil.controller;

import GpsUtil.service.GpsUtilService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class GpsUtilController {

    private static final Logger logger = LoggerFactory.getLogger("GpsUtilControllerLog");

    @Autowired
    GpsUtilService gpsUtilService;

    /**
     *  Get Index Controller
     *
     * @return String Greetings from GpsUtil!
     */
    @RequestMapping("/")
    public String index() {
        logger.info("Get gpsUtil index");
        return "Greetings from GpsUtil!";
    }

    /**
     * getUserLocation and return user position
     *
     * @param userId User id from position
     * @return VisitedLocation user position with timestamp
     */
    @RequestMapping("/userLocation")
    public VisitedLocation getUserLocation (@RequestParam UUID userId) {
        logger.info("Get visited location for user with {}", userId);
        return gpsUtilService.getUserLocation(userId);
    }

    /**
     * getAllAttractions and returns all attractions
     * @return List of all Attractions
     */
    @RequestMapping("/getAttractions")
    public List<Attraction> getAllAttractions() {
        logger.info("Get all attractions list");
        return gpsUtilService.getAllAttractions();
    }
}