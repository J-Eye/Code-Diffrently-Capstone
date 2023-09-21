package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.CallException;
import irby.jaden.namepending.models.Call;
import irby.jaden.namepending.repo.CallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CallServiceImpl implements CallService {

    private CallRepo callRepo;

    @Autowired
    public CallServiceImpl(CallRepo callRepo) {
        this.callRepo = callRepo;
    }


    @Override
    public Call saveCall(Call call) {
        return callRepo.save(call);
    }

    @Override
    public List<Call> getAllCalls() {
        return callRepo.findAll();
    }

    @Override
    public Call getById(int id) throws CallException {
        Optional<Call> optionalCall = callRepo.findById(id);

        if(optionalCall.isEmpty()){
            throw new CallException("Call record at id "+id+" doesn't exist");
        }
        return optionalCall.get();
    }
}
