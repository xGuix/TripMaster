package tourGuide;

import com.dto.UserDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.UserProxy;
import tourGuide.service.RewardService;
import tourGuide.service.TourGuideService;
import tourGuide.service.TrackerService;
import tourGuide.util.InternalTestDataSet;
import tourGuide.util.InternalTestHelper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TestPerformanceIT {
	static final Logger logger = LogManager.getLogger("TourGuidePerformanceLog");

	@Autowired
	UserProxy userProxy;
	@Autowired
	GpsUtilProxy gpsUtilProxy;
	@Autowired
	RewardCentralProxy rewardCentralProxy;
	@Autowired
	TripPricerProxy tripPricerProxy;

	@Autowired
	RewardService rewardService;
	@Autowired
	TrackerService trackerService;

	/*
	 * A note on performance improvements:
	 *
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     		userService.setInternalUserNumber(100000);
	 *
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent.
	 *
	 *     These are performance metrics that we are trying to hit:
	 *
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 *
	 *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */
	@BeforeAll
	static void setupUser(){
		InternalTestHelper.setInternalUserNumber(100000);
	}

	@Test
	public void highVolumeTrackLocation() {
		// Users should be incremented up to 100,000 and test finishes within 3 minutes
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();

		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);
	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<UserDto> allUsersList = internalTestDataSet.getAllUsers();
		allUsersList.parallelStream().forEach(u -> {
			tourGuideService.trackUserLocation(u.getUserId()).join();
		});

		assertTrue(allUsersList.get(0).getVisitedLocations().size()>1);
		assertEquals(100000, allUsersList.size());

		stopWatch.stop();
		trackerService.stopTracking();

		logger.info("highVolumeTrackLocation - Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(3) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

	@Test
	public void highVolumeGetRewards() {
		// Users should be incremented up to 100,000 and test finishes within 5 minutes
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		internalTestDataSet.initializeInternalUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<UserDto> allUsersList = internalTestDataSet.getAllUsers();
		Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		Location location = new Location(attraction.getLongitude(), attraction.getLatitude());

		allUsersList.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), location, new Date())));

		CompletableFuture<?> completableFutures = CompletableFuture.runAsync(() ->  allUsersList.parallelStream().forEach(u -> rewardService.calculateRewards(u)));
		CompletableFuture.allOf(completableFutures).join();

//		CompletableFuture<?>[] completableFutures = allUsersList.parallelStream().map(rewardService::calculateRewards).toArray(CompletableFuture[]::new);
//		CompletableFuture.allOf(completableFutures).join();

		for(UserDto userDto : allUsersList) {
			assertNotEquals(0,userDto.getUserRewards().get(0).getRewardPoints());
			assertTrue(userDto.getUserRewards().size() > 0);
		}

		stopWatch.stop();
		trackerService.stopTracking();

		logger.info("highVolumeGetRewards - Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(10) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertEquals(100000, allUsersList.size());
	}
}