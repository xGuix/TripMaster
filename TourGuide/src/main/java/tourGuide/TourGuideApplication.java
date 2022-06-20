package tourGuide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.Locale;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"tourGuide.util"})
public class TourGuideApplication {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try {
        SpringApplication.run(TourGuideApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}