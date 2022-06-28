package tourGuide.service;

import com.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RewardServiceTest {

    @Autowired
    RewardService rewardService;

    UserDto user;

    @BeforeEach
    void setupTest() {
        user = new UserDto(UUID.fromString("648ed5ea-b766-4aee-a0b7-3686e166c977"), "username", "000", "test@test.com");
    }

    @Test
    void calculateReward() throws URISyntaxException {
        CompletableFuture<?> result;
        result = rewardService.calculateRewards(user);
        assertNotNull(result);
    }
}
