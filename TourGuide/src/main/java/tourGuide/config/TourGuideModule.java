package tourGuide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;

@Configuration
public class TourGuideModule {

	@Bean
	public GpsUtilProxy getGpsUtil() {
		return getGpsUtil();
	}

	@Bean
	public RewardCentralProxy getRewardCentral() {
		return getRewardCentral();
	}
}