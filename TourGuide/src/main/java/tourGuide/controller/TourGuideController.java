package tourGuide.controller;

import com.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.service.TourGuideService;

import java.util.List;

/**
 *  Tour Guide rest controller
 */
@RestController
public class TourGuideController {

    private static final Logger logger = LogManager.getLogger("TourGuideControllerLog");

    /**
     *  Load TourGuideService
     */
	@Autowired
	TourGuideService tourGuideService;

    /**
     *  Get Index Controller
     *
     * @return String Greetings from TourGuide!
     */
    @RequestMapping("/")
    public String index() {
        logger.info("Get index");
        return "Greetings from TourGuide!";
    }

    /**
     * Get All user:
     * Call to get All user with username
     *
     * @return userList List of all users
     */
    @RequestMapping("/getUsers")
    public List<UserDto> getUsers() {
        logger.info("Call service for list of all users");
        return tourGuideService.getUsers();
    }

    /**
     * Get user:
     * Call to get user with username
     *
     * @param userName String userName
     * @return userName User userName
     */
    @RequestMapping("/getUser")
    public UserDto getUser(@RequestParam String userName) {
        logger.info("Call service for user with username: {}", userName);
        return tourGuideService.getUser(userName);
    }
//
//    /**
//     *  Get user location
//     *  Call get location with username
//     *
//     * @param userName String user name
//     * @return visitedLocation The visited location
//     */
//    @RequestMapping("/getLocation")
//    public VisitedLocation getLocation(@RequestParam String userName) {
//    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
//        logger.info("Get visited locations with username: {}", userName);
//		return visitedLocation;
//    }
//
//    /**
//     * Get the closest attractions with distance from user :
//     * Call to get attractions  with username
//     *
//     * @param userName String user name
//     * @return userAttractionList List of the closest attraction
//     */
//    @RequestMapping("/getNearbyAttractions")
//    public List<NearbyAttractionsDto> getNearByAttractions(@RequestParam String userName) {
//    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
//        List<NearbyAttractionsDto> userAttractionList = tourGuideService.getNearByAttractions(visitedLocation);
//        logger.info("Get nearby attractions with username: {}", userName);
//    	return userAttractionList;
//    }
//
//    /**
//     * Get user rewards:
//     * Call to get user rewards with username
//     *
//     * @param userName String userName
//     * @return providers List of providers
//     */
//    @RequestMapping("/getRewards")
//    public List<UserRewardDto> getRewards(@RequestParam String userName) {
//        List<UserRewardDto> userRewardDtoList = tourGuideService.getUserRewards(getUser(userName));
//        logger.info("Get user reward with username: {}", userName);
//    	return userRewardDtoList;
//    }
//
//    /**
//     * Get all current locations:
//     * Call to get all current locations with username
//     *
//     * @return allUsers List of all users locations
//     */
//    @RequestMapping("/getAllCurrentLocations")
//    public Map<UUID, Location> getAllCurrentLocations() {
//        Map<UUID, Location> userLocationMap = new HashMap<>();
//        List<UserDto> usersList = getAllUsers();
//        for (UserDto users: usersList){
//            VisitedLocation userVisitedLocation = getLocation(users.getUserName());
//            userLocationMap.put(userVisitedLocation.getUserId(), userVisitedLocation.getLocation());
//        }
//        logger.info("Get all users current Location");
//    	return userLocationMap;
//    }
//
//    /**
//     * Get List of providers:
//     * Call to get trip deals with username
//     *
//     * @param userName String userName
//     * @return providers List of providers
//     */
//    @RequestMapping("/getTripDeals")
//    public List<Provider> getTripDeals(@RequestParam String userName) {
//    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
//        logger.info("Get user trip deal with username: {}", userName);
//    	return providers;
//    }
}