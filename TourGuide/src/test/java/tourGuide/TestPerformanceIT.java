package tourGuide;

import com.dto.UserDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.TripPricerProxy;
import tourGuide.util.InternalTestDataSet;
import tourGuide.util.InternalTestHelper;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
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
	TourGuideService tourGuideService;

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

	@Test
	public void highVolumeTrackLocation() {
		Executor executor = Executors.newFixedThreadPool(100);
		// Users should be incremented up to 100,000 and test finishes within 15 minutes
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(100000);
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);

		List<UserDto> allUsersDto = userProxy.getUsers();
		ArrayList<CompletableFuture> completableFutures= new ArrayList<>();

	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		allUsersDto.forEach(u -> {
			CompletableFuture completable = CompletableFuture.runAsync(() -> {
				tourGuideService.trackUserLocation(u.getUserId());
				}, executor);
			completableFutures.add(completable);
		});
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();

//		List<UUID> allUsersId = allUsersDto.stream().map(userDto -> userDto.getUserId()).collect(Collectors.toList());
//		List<CompletableFuture<Void>> result = allUsersId.stream()
//				.map(userId -> CompletableFuture.runAsync(() -> tourGuideService.trackUserLocation(userId), executor))
//						.collect(Collectors.toList());
//		result.forEach(CompletableFuture::join);

		stopWatch.stop();

		logger.info("highVolumeTrackLocation - Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

	@Test
	public void highVolumeGetRewards() {
		Executor executor = Executors.newFixedThreadPool(100);
		// Users should be incremented up to 100,000 and test finishes within 20 minutes
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(100000);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);

	    Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		List<UserDto> allUsersDto = userProxy.getUsers();
		List<CompletableFuture> completableFutures = new ArrayList<>();

		allUsersDto.forEach(u ->
				u.addToVisitedLocations(
						new VisitedLocation(u.getUserId(),
						new Location(attraction.getLongitude(),attraction.getLatitude()),
						new Date())));

		allUsersDto.forEach(u -> {
			CompletableFuture completable = CompletableFuture.runAsync(() -> {
				rewardService.calculateRewards(u);
				}, executor);
			completableFutures.add(completable);
		});
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();

		for(UserDto userDto : allUsersDto) {
			assertTrue(userDto.getUserRewards().size() > 0);
		}

		stopWatch.stop();

		logger.info("highVolumeGetRewards - Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
}