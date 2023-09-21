package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.CallException;
import irby.jaden.namepending.models.Call;

import java.util.List;


public interface CallService {
    Call saveCall(Call call);
    List<Call> getAllCalls();
    Call getById(int id) throws CallException;
}
