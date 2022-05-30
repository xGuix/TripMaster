package tourGuide.service;

import com.dto.UserDto;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.proxy.UserProxy;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Tracker Service
 */
@Service
public class TrackerService extends Thread {

//	private final Logger logger = LoggerFactory.getLogger(TrackerService.class);
//	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//	private final TourGuideService tourGuideService;
//	private final RewardCentralProxy rewardsService;
//	private final UserProxy userService;
//	private boolean stop = false;
//
//	/**
//	 * Constructor
//	 *
//	 * @param tourGuideService TourGuideService
//	 * @param rewardsService RewardService
//	 */
//	TrackerService(TourGuideService tourGuideService, UserProxy userService, RewardCentralProxy rewardsService) {
//		this.tourGuideService = tourGuideService;
//		this.userService = userService;
//		this.rewardsService = rewardsService;
//		executorService.submit(this);
//	}
//
//	/**
//	 * Assures to shut down the Tracker thread
//	 */
//	public void stopTracking() {
//		stop = true;
//		executorService.shutdownNow();
//	}
//
//	/**
//	 * Run
//	 */
//	@Override
//	public void run() {
//		StopWatch stopWatch = new StopWatch();
//		ExecutorService trackExecutor = Executors.newFixedThreadPool(1);
//		ExecutorService rewardExecutor = Executors.newFixedThreadPool(1);
//
//			List<UserDto> userDtoList = userService.getUsers();
//			logger.debug("Begin Tracker. Tracking {} users.", userDtoList.size());
//
//			stopWatch.start();
//			List<CompletableFuture<?>> trackResult = userDtoList.stream()
//							.map(userDto -> CompletableFuture.runAsync(() -> tourGuideService.trackUserLocation(userDto), trackExecutor)
//									.thenRunAsync(() -> rewardsService.calculateRewards(userDto),rewardExecutor))
//					.collect(Collectors.toList());
//			trackResult.forEach(CompletableFuture::join);
//			stopWatch.stop();
//
//			logger.debug("Tracker  Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
}