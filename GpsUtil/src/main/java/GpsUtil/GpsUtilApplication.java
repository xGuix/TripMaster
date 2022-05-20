package GpsUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("TripMaster.GpsUtil")
public class GpsUtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(GpsUtilApplication.class, args);
    }
}