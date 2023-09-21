package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.UserException;
import irby.jaden.namepending.models.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity registerUser(UserEntity userEntity) throws UserException;
    UserEntity loginUser(String email, String password) throws UserException;
    UserEntity updateUser(UserEntity userEntity, int id) throws UserException;
    Boolean deleteUser(int id) throws UserException;
    UserEntity getById(int id) throws UserException;
    List<UserEntity> getAll();
}
