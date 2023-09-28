package irby.jaden.namepending.controllers;

import irby.jaden.namepending.Execeptions.CallException;
import irby.jaden.namepending.models.Call;
import irby.jaden.namepending.serivce.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/call")
@CrossOrigin("*")
public class CallController {

    private final CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping
    public ResponseEntity<Call> create(@RequestBody Call call){
        Call createdCall = callService.saveCall(call);
        return new ResponseEntity<>(createdCall, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Call> getById(@PathVariable int id) throws CallException {
        Call call = callService.getById(id);
        return new ResponseEntity<>(call, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Call>> getAll(){
        List<Call> calls = callService.getAllCalls();
        return new ResponseEntity<>(calls, HttpStatus.OK);
    }
}
