package tourGuide.util;

import com.dto.UserDto;
import com.dto.UserRewardDto;
import com.model.Attraction;
import com.model.Location;
import com.model.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InternalTestDataSet {
    public final Logger logger = LoggerFactory.getLogger(InternalTestDataSet.class);
    public static final String tripPricerApiKey = "test-server-api-key";
    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
    public final Map<String, UserDto> internalUserMap = new HashMap<>();
    public void initializeInternalUsers() {
        logger.info("-----------------------------TestMode enabled-----------------------------");
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            UserDto userDto = new UserDto(UUID.randomUUID(), userName, phone, email);
            generateUserLocationHistory(userDto);
            UserRewardDto userReward = new UserRewardDto(userDto.getLastVisitedLocation(), new Attraction("LalaLand","Miami","Florida", UUID.randomUUID()),100);
            userDto.getUserRewards().add(userReward);
            internalUserMap.put(userName, userDto);
        });
        logger.info("Created " + InternalTestHelper.getInternalUserNumber() + " internal users.");
        logger.debug("Initializing {} users", internalUserMap.size());
        logger.debug("-----------------------Finished initializing users-----------------------");
    }

    public List<UserDto> getAllUsers() {
       return internalUserMap.values().stream().collect(Collectors.toList());
    }


    public void generateUserLocationHistory(UserDto user) {
        IntStream.range(0, 3).forEach(i-> {
            user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
        });
    }

    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    public Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(69));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}