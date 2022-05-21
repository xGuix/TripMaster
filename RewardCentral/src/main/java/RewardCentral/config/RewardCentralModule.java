package RewardCentral.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

@Configuration
public class RewardCentralModule {

	/**
	 * Gets reward central.
	 *
	 * @return the reward central
	 */
	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}
}