package irby.jaden.namepending.Service;

import irby.jaden.namepending.Execeptions.CallException;
import irby.jaden.namepending.models.Call;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.repo.CallRepo;
import irby.jaden.namepending.serivce.CallService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CallServiceImplTest {

    @MockBean
    private CallRepo mockCallRepo;

    @Autowired
    private CallService callService;


    private Call inputCall;
    private Call responseCall1;
    private Call responseCall2;


    @BeforeEach
    public void setUp(){
        inputCall = new Call(new ArrayList<>());
        inputCall.getParticipants().add(new UserEntity());
        inputCall.getParticipants().add(new UserEntity());
        inputCall.setId(1);

        responseCall1 = new Call(new ArrayList<>());
        responseCall1.setId(2);
        responseCall2 = new Call(new ArrayList<>());
        responseCall2.setId(3);
    }

    @Test
    public void createCallSuccess(){
        BDDMockito.doReturn(responseCall1).when(mockCallRepo).save(ArgumentMatchers.any());
        Call returnedCall = callService.saveCall(inputCall);
        Assertions.assertNotNull(returnedCall, "Call should not be null");
    }

    @Test
    public void getCallByIdSuccess() throws CallException {
        BDDMockito.doReturn(Optional.of(responseCall1)).when(mockCallRepo).findById(ArgumentMatchers.any());
        Call returnedCall = callService.getById(2);

        Assertions.assertEquals(responseCall1.toString(), returnedCall.toString());

    }

    @Test
    public void getCallByIdFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockCallRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(CallException.class, () ->{
            callService.getById(1);
        });
    }

    @Test
    public void getAllCallsSuccess(){
        List<Call> calls = new ArrayList<>();
        calls.add(responseCall1);
        calls.add(responseCall2);

        BDDMockito.doReturn(calls).when(mockCallRepo).findAll();
        List<Call> returnedCals = callService.getAllCalls();
        Assertions.assertNotNull(returnedCals, "Calls should not be null");
        Assertions.assertIterableEquals(calls, returnedCals);
    }
}
