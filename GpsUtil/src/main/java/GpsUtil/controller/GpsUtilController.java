package GpsUtil.controller;

import GpsUtil.service.GpsUtilService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class GpsUtilController {

    private Logger logger = LoggerFactory.getLogger("GpsUtilControllerLog");

    @Autowired
    GpsUtilService gpsUtilService;

    /**
     * getUserLocation and return user position
     *
     * @param userId User id from position
     * @return VisitedLocation user position with timestamp
     */
    @GetMapping(value="/userLocation")
    public VisitedLocation getUserLocation (@RequestParam UUID userId) {
        logger.info("Controller request /userLocation with {}", userId);
        return gpsUtilService.getUserLocation(userId);
    }

    /**
     * getAllAttractions and returns all attractions
     * @return List of all Attractions
     */
    @GetMapping(value="/getAttractions")
    public List<Attraction> getAllAttractions() {
        logger.info("Controller request /getAttractions from GpsUtilApp");
        return gpsUtilService.getAllAttractions();
    }
}