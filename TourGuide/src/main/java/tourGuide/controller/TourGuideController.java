package tourGuide.controller;

import com.dto.NearbyAttractionsDto;
import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.dto.UserRewardDto;
import com.model.Provider;
import com.model.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tourGuide.service.TourGuideService;

import java.util.List;
import java.util.UUID;

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
        logger.info("Get tour guide index");
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
     * @return userDto User the user
     */
    @RequestMapping("/getUser")
    public UserDto getUser(@RequestParam String userName) {
        logger.info("Call service for user with username: {}", userName);
        return tourGuideService.getUser(userName);
    }

    /**
     * Add user:
     * @param userDto UserDto user
     */
    @PostMapping("/addUser")
    public void addUser(@RequestBody UserDto userDto){
        logger.info("Call service for add user: {}", userDto);
        tourGuideService.addUser(userDto);
    };

    /**
     * Get user:
     * Call to get user with id
     *
     * @param userId UUID user id
     * @return userName User userName
     */
    @RequestMapping("/getUserById")
    public UserDto getUserById(@RequestParam UUID userId) {
        logger.info("Call service for user with user id: {}", userId);
        return tourGuideService.getUserById(userId);
    }

    /**
     *  Get user location
     *  Call get location with username
     *
     * @param userId String user name
     * @return visitedLocation The visited location
     */
    @RequestMapping("/getLocation")
    public VisitedLocation getLocation(@RequestParam UUID userId) {
        logger.info("Get user locations with userId: {}", userId);
        //UserDto userDto = getUser(userName);
		return tourGuideService.getUserLocation(userId);
    }

    /**
     * Get all current locations:
     * Call to get all current locations with username
     *
     * @return allUsers List of all users locations
     */
    @RequestMapping("/getAllCurrentLocations")
    public List<UserLocationDto> getAllCurrentLocations() {
        logger.info("Get all users current Location");
        return tourGuideService.getAllCurrentLocations();
    }

    /**
     * Get the closest attractions with distance from user :
     * Call to get attractions  with username
     *
     * @param userName String user name
     * @return userAttractionList List of the closest attraction
     */
    @GetMapping("/getNearbyAttractions")
    public List<NearbyAttractionsDto> getNearbyAttractions(@RequestParam String userName) {
        logger.info("Get nearby attractions with username: {}", userName);
        UserDto userDto = tourGuideService.getUser(userName);
    	return tourGuideService.getNearbyAttractions(userDto);
    }

    /**
     * Get user rewards:
     * Call to get user rewards with username
     *
     * @param userName String userName
     * @return providers List of providers
     */
    @RequestMapping("/getRewards")
    public List<UserRewardDto> getRewards(@RequestParam String userName) {
        logger.info("Get user reward with username: {}", userName);
    	return tourGuideService.getRewards(userName);
    }

    /**
     * Add user rewards:
     * Call to add user rewards
     *
     * @param userName String userName
     * @return providers List of providers
     */
    @PostMapping("/addRewards")
    public void addRewards(@RequestParam String userName, @RequestBody UserRewardDto userReward) {
        logger.info("Add user reward with username: {} reward {}", userName,userReward);
        tourGuideService.addRewards(userName,userReward);
    }

    /**
     * Get List of providers:
     * Call to get trip deals with username
     *
     * @param userName String userName
     * @return providers List of providers
     */
    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        logger.info("Get user trip deal with username: {}", userName);
        UserDto userDto = getUser(userName);
    	return tourGuideService.getTripDeals(userDto);
    }
}