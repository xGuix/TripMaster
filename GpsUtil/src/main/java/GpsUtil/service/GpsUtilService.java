package GpsUtil.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GpsUtilService {

    private final Logger logger = LoggerFactory.getLogger("GpsUtilServiceLog");
    private final GpsUtil gpsUtil = new GpsUtil();

    /**
     * getUserLocation to Get the user location
     *
     * @param userId User id
     * @return visitedLocation from Jar
     */
    public VisitedLocation getUserLocation(UUID userId) {
        logger.info("Service visited location for userId : {}", userId);
        return gpsUtil.getUserLocation(userId);
    }

    /**
     * getUserLocation to Get the user location
     *
     * @return List of all attraction from Jar
     */
    public List<Attraction> getAllAttractions() {
        logger.info("Service getAllAttractions from gpsUtil");
        return gpsUtil.getAttractions();
    }

    //VisitedLocationDto getUserLastVisitedLocation(UUID userId)
    //void addVisitedLocation(UUID userId, List<VisitedLocationDto> visitedLocationDto);
    //List<AttractionWithDistanceDto> getNearbyAttractions(UUID userId, int limit) throws NoLocationFoundException;

//    @Override
//    public List<VisitedAttractionDto> getVisitedAttractions(UUID userId) {
//        List<Attraction> attractions = gpsUtil.getAttractions();
//        List<VisitedLocation> visitedLocations = locationHistoryRepository.findById(userId);
//        List<VisitedAttractionDto> visitedAttraction = new ArrayList<>();
//        // For each attraction we search the first visited location in range
//        attractions.forEach(attraction -> visitedLocations
//                .stream()
//                .filter(visitedLocation -> isInRangeOfAttraction(visitedLocation, attraction))
//                .findFirst()
//                .ifPresent(visitedLocation -> visitedAttraction.add(new VisitedAttractionDto(
//                        AttractionMapper.toDto(attraction),
//                        VisitedLocationMapper.toDto(visitedLocation)
//                )))
//        );
//        return visitedAttraction;
//    }

}