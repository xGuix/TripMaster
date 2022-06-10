package tourGuide;

import com.dto.UserDto;
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
import tourGuide.service.TourGuideService;
import tourGuide.service.TrackerService;
import tourGuide.util.InternalTestDataSet;
import tourGuide.util.InternalTestHelper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

	@Test
	public void highVolumeTrackLocation() {
		Executor executor = Executors.newFixedThreadPool(100);
		// Users should be incremented up to 100,000 and test finishes within 15 minutes
		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
		InternalTestHelper.setInternalUserNumber(100000);

		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet,userProxy, gpsUtilProxy, rewardCentralProxy, tripPricerProxy);

		Collection<UserDto> allUsersDto = internalTestDataSet.internalUserMap.values();
		ArrayList<CompletableFuture> completableFutures= new ArrayList<>();

	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		allUsersDto.forEach(u -> {
			CompletableFuture completable = CompletableFuture.runAsync(() -> {
				logger.info("---  userName : {}  ---",u.getUserName());
				tourGuideService.trackUserLocation(u.getUserId());
				}, executor);
			completableFutures.add(completable);
		});
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();

//		List<UUID> allUsersId = allUsersDto.stream().map(userDto -> userDto.getUserId()).collect(Collectors.toList());
//		List<CompletableFuture<Void>> result = allUsersId.stream()
//				.map(userId -> CompletableFuture.runAsync(() ->
//					tourGuideService.trackUserLocation(userId), executor))
//						.collect(Collectors.toList());
//		result.forEach(CompletableFuture::join);

		stopWatch.stop();
		trackerService.stopTracking();

		logger.info("highVolumeTrackLocation - Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(5) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertEquals(100000, allUsersDto.size());
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

		Collection<UserDto> allUsersDto = internalTestDataSet.internalUserMap.values();
		List<CompletableFuture> completableFutures = new ArrayList<>();

		allUsersDto.forEach(u -> {
			CompletableFuture completable = CompletableFuture.runAsync(() -> {
				logger.info("--- userName : {} ---", u.getUserName());
				tourGuideService.rewardService.calculateRewards(u);
				logger.info("--- Visited Location : {} ---", u.getLastVisitedLocation());
				}, executor);
			completableFutures.add(completable);
		});
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();

		for(UserDto userDto : allUsersDto) {
			assertTrue(userDto.getUserRewards().size() > 0);
		}

		stopWatch.stop();
		trackerService.stopTracking();

		logger.info("highVolumeGetRewards - Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertEquals(100000, allUsersDto.size());
	}
}