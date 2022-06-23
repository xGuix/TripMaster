package RewardCentral.controller;

import RewardCentral.service.RewardCentralService;
import com.dto.UserDto;
import com.model.Attraction;
import com.model.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RewardCentralController.class)
public class RewardCentralControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RewardCentralService rewardCentralService;

    UserDto user = new UserDto(UUID.randomUUID(), "BobLazar");

    @Test
    void getRewardPointTest() throws Exception {
        int rewardPoint = 500;
        UUID attractionId = UUID.randomUUID();
        Mockito.when(rewardCentralService.getRewardPoints(attractionId, user.getUserId())).thenReturn(rewardPoint);
        mockMvc.perform(get("/getRewardPoints")
                .param("attractionId", String.valueOf(attractionId))
                .param("userId", String.valueOf(user.getUserId())))
                .andExpect(status().isOk());
    }

}