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
	 * @param attraction Attraction
	 * @param userDto UserDto
	 * @return int number of reward points
	 */
	public int getRewardPoints(Attraction attraction, UserDto userDto) {
		logger.info("Get user reward points");
		return rewardCentral.getAttractionRewardPoints(attraction.getAttractionId(), userDto.getUserId());
	}
}