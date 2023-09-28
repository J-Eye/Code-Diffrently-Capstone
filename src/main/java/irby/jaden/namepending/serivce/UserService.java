package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.UserAlreadyExistsException;
import irby.jaden.namepending.Execeptions.UserNotFoundException;
import irby.jaden.namepending.models.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity registerUser(UserEntity userEntity) throws UserAlreadyExistsException;
    UserEntity loginUser(String email, String password) throws UserNotFoundException;
    UserEntity updateUser(UserEntity userEntity, int id) throws UserNotFoundException;
    Boolean deleteUser(int id) throws UserNotFoundException;
    UserEntity getById(int id) throws UserNotFoundException;
    List<UserEntity> getAll();
}
