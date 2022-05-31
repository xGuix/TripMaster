package TripPricer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tripPricer.TripPricer;

@Configuration
public class TripPricerModule {

	/**
	 * Gets reward central.
	 * @return the reward central
	 */
	@Bean
	public TripPricer getTripPricer() {
		return new TripPricer();
	}
}