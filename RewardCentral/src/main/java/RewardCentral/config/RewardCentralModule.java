package RewardCentral.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardCentralModule {

	@Bean
	public RewardCentralModule getRewardCentral() {
		return new RewardCentralModule();
	}
}