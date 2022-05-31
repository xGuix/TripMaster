package TripPricer.controller;

import TripPricer.service.TripPricerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

@RestController
public class TripPricerController {

    private static final Logger logger = LogManager.getLogger("TripPricerControllerLog");

    @Autowired
    TripPricerService tripPricerService;

    /**
     *  Get Index Controller
     *
     * @return String Greetings from TripPricer!
     */
    @RequestMapping("/")
    public String index() {
        logger.info("Get trip pricer index");
        return "Greetings from TripPricer!";
    }

    @RequestMapping("/getPrice")
    public List<Provider> getPrice(@RequestParam("apiKey") String apiKey,
                                   @RequestParam("attractionId") UUID attractionId,
                                   @RequestParam("adults") int adults,
                                   @RequestParam("children") int children,
                                   @RequestParam("nightsStay") int nightsStay,
                                   @RequestParam("rewardsPoints") int rewardsPoints ){
        return tripPricerService.getPrice(apiKey,attractionId,adults,children,nightsStay,rewardsPoints);
    }

}
