package tourGuide.service;

import com.dto.UserDto;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	private final Logger logger = LoggerFactory.getLogger("TrackerServiceLog");
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final UserProxy userProxy;
	private boolean stop = false;

	@Autowired
	TourGuideService tourGuideService;

	@Autowired
    RewardService rewardService;

	/**
	 * Constructor
	 * @param tourGuideService TourGuideService
	 * @param userProxy RewardService
	 */
	public TrackerService(TourGuideService tourGuideService, UserProxy userProxy) {
		this.tourGuideService = tourGuideService;
		this.userProxy = userProxy;
		executorService.submit(this);
	}

	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		stop = true;
		executorService.shutdownNow();
	}

	/**
	 * Run tracker
	 */
	@Override
	public void run() {
		StopWatch stopWatch = new StopWatch();
		ExecutorService trackExecutor = Executors.newSingleThreadExecutor();
		ExecutorService rewardExecutor = Executors.newSingleThreadExecutor();

			List<UserDto> userDtoList = userProxy.getUsers();
			logger.debug("Begin Tracker. Tracking {} users.", userDtoList.size());

			stopWatch.start();
			List<CompletableFuture<?>> trackResult = userDtoList.stream()
							.map(userDto -> CompletableFuture.runAsync(() -> tourGuideService.getUserLocation(userDto.getUserId()), trackExecutor)
									.thenRunAsync(() -> rewardService.calculateRewards(userDto),rewardExecutor))
					.collect(Collectors.toList());
			trackResult.forEach(CompletableFuture::join);
			stopWatch.stop();

			logger.debug("Tracker  Time Elapsed: {} seconds.", TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
}