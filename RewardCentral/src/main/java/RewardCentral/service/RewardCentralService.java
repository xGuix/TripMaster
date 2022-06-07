package RewardCentral.service;

import com.dto.UserDto;
import com.dto.UserLocationDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

/**
 * Reward Service
 */
@Service
public class RewardCentralService {

    private final Logger logger = LoggerFactory.getLogger("RewardCentralServiceLog");
	private final RewardCentral rewardCentral = new RewardCentral();

	/**
	 * Get user reward points
	 *
	 * @param attractionId Attraction id
	 * @param userId User id
	 * @return int number of reward points
	 */
	public int getRewardPoints(UUID attractionId, UUID userId) {
		logger.info("Get user reward points with UUID attraction: {} and  UUID user: {}", attractionId, userId);
		return rewardCentral.getAttractionRewardPoints(attractionId, userId);
	}
}