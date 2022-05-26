package tourGuide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.helper.beans"})
@EnableFeignClients("tourGuide.proxy")
public class TourGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourGuideApplication.class, args);
    }
}