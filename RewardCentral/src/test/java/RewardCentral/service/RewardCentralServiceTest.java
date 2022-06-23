package RewardCentral.service;

import com.dto.UserDto;
import com.model.Attraction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import rewardCentral.RewardCentral;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class RewardCentralServiceTest {

    @MockBean
    RewardCentral rewardCentral;

    UserDto user = new UserDto(UUID.randomUUID(), "BobLazar");
    Attraction attraction = new Attraction("Lala land","City","State",UUID.randomUUID());

    @Test
    void getRewardPoints(){
        rewardCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
        verify(rewardCentral, Mockito.times(1)).getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
    }
}
