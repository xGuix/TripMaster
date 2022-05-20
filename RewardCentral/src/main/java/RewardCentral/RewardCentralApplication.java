package RewardCentral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("TripMaster.RewardCentral")
public class RewardCentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardCentralApplication.class, args);
    }
}