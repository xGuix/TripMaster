//package tourGuide;
//
//import com.dto.UserDto;
//import com.helper.InternalTestDataSet;
//import com.helper.InternalTestHelper;
//import com.model.Attraction;
//import com.model.Location;
//import com.model.VisitedLocation;
//import org.apache.commons.lang3.time.StopWatch;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import tourGuide.proxy.GpsUtilProxy;
//import tourGuide.proxy.RewardCentralProxy;
//import tourGuide.proxy.TripPricerProxy;
//import tourGuide.service.TourGuideService;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//public class TestPerformanceIT {
//
//	static final Logger logger = LogManager.getLogger("TourGuidePerformanceLog");
//
//	@Autowired
//	private GpsUtilProxy gpsUtil;
//
//	@Autowired
//	private RewardCentralProxy rewardCentral;
//
//	@Autowired
//	private TripPricerProxy tripPricer;
//
//	/**
//	 * Create ThreadPool of 1000
//	 */
//	Executor executor = Executors.newFixedThreadPool(100);
//	/*
//	 * A note on performance improvements:
//	 *
//	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
//	 *
//	 *     		InternalTestHelper.setInternalUserNumber(100000);
//	 *
//	 *
//	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
//	 *     at the end of the tests remains consistent.
//	 *
//	 *     These are performance metrics that we are trying to hit:
//	 *
//	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
//	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//     *
//     *     highVolumeGetRewards: 100,000 users within 20 minutes:
//	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	 */
//
//	@Test
//	public void highVolumeTrackLocation() {
//		// Users should be incremented up to 100,000, and test finishes within 15 minutes
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1000);
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//		List<UserDto> allUsersDto;
//		ArrayList<CompletableFuture> completableFutures= new ArrayList<>();
//		allUsersDto = tourGuideService.getAllUsers();
//
//	    StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//
//		allUsersDto.forEach(u -> {
//			CompletableFuture completable = CompletableFuture.runAsync(() -> {
//				tourGuideService.trackUserLocation(u);
//				}, executor);
//			completableFutures.add(completable);
//		});
//		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
//
//		stopWatch.stop();
//
//		logger.info("highVolumeTrackLocation / Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
//
//	@Test
//	public void highVolumeGetRewards() {
//		// Users should be incremented up to 100,000 and test finishes within 20 minutes
//		InternalTestDataSet internalTestDataSet = new InternalTestDataSet();
//		InternalTestHelper.setInternalUserNumber(1000);
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		TourGuideService tourGuideService = new TourGuideService(internalTestDataSet, gpsUtil, rewardCentral, tripPricer);
//
//	    Attraction attraction = gpsUtil.getAttractions().get(0);
//		List<UserDto> allUsersDto;
//		List<CompletableFuture> completableFutures = new ArrayList<>();
//		allUsersDto = tourGuideService.getAllUsers();
//		allUsersDto.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), new Location(attraction.getLongitude(),attraction.getLatitude()), new Date())));
//
//		allUsersDto.forEach(u -> {
//			CompletableFuture completable = CompletableFuture.runAsync(() -> {
//				rewardCentral.calculateRewards(u);
//				}, executor);
//			completableFutures.add(completable);
//		});
//		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
//
//		for(UserDto userDto : allUsersDto) {
//			assertTrue(userDto.getUserRewards().size() > 0);
//		}
//
//		stopWatch.stop();
//
//		logger.info("highVolumeGetRewards / Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
//}