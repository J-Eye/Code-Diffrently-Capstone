package irby.jaden.namepending.controllers;

import irby.jaden.namepending.Execeptions.UserException;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.serivce.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity userEntity) throws UserException {
        UserEntity createdUserEntity = userService.registerUser(userEntity);
        return new ResponseEntity<>(createdUserEntity, HttpStatus.OK);
    }

    @PutMapping("/update{id}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity, @PathVariable int id) throws UserException {
        UserEntity updatedUserEntity = userService.updateUser(userEntity, id);
        return new ResponseEntity<>(updatedUserEntity, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserEntity>> getAll(){
        List<UserEntity> userEntityList = userService.getAll();
        return new ResponseEntity<>(userEntityList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable int id) throws UserException {
        UserEntity userEntity = userService.getById(id);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable int id) throws UserException {
        Boolean result = userService.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
