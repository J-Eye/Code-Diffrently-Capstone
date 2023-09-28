package irby.jaden.namepending.Controller;

import irby.jaden.namepending.Execeptions.CallException;
import irby.jaden.namepending.controllers.CallController;
import irby.jaden.namepending.models.Call;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.serivce.CallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CallControllerTest extends BaseControllerTest{

    @MockBean
    private CallService mockCallService;

    @InjectMocks
    private CallController callController;

    @Autowired
    private MockMvc mockMvc;

    private Call inputCall;
    private Call responseCall1;
    private Call responseCall2;

    @BeforeEach
    public void setUp(){
        inputCall = new Call(new ArrayList<UserEntity>());
        responseCall1 = new Call();
        responseCall2 = new Call();
    }

    @Test
    public void createCallSuccess() throws Exception {
        BDDMockito.doReturn(responseCall1)
                .when(mockCallService).saveCall(ArgumentMatchers.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputCall)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void getCallByIdSuccess() throws Exception {
        BDDMockito.doReturn(responseCall1)
                .when(mockCallService).getById(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/call/{id}",1)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getCallByIdFail() throws Exception {
        BDDMockito.doThrow(new CallException("Call record at id 1 doesn't exist"))
                .when(mockCallService).getById(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/call/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getAllCallsSuccess() throws Exception {
        List<Call> callList = new ArrayList<>();
        callList.add(responseCall1);
        callList.add(responseCall2);

        BDDMockito.doReturn(callList)
                .when(mockCallService).getAllCalls();

        mockMvc.perform(MockMvcRequestBuilders.get("/call")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
