package irby.jaden.namepending.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import irby.jaden.namepending.models.User;
import irby.jaden.namepending.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/app")
@CrossOrigin("*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity<UserRecord> registerUser(@RequestBody User user) throws FirebaseAuthException {
        UserRecord createdUser = userService.RegisterUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
}
