package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.UserAlreadyExistsException;
import irby.jaden.namepending.Execeptions.UserNotFoundException;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity registerUser(UserEntity userEntity) throws UserAlreadyExistsException {

        Optional<UserEntity> optionalUser = userRepo.findByUsername(userEntity.getUsername());
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("Account with username "+ userEntity.getUsername()+" exists");
        }
        return userRepo.save(userEntity);
     }

    @Override
    public UserEntity loginUser(String email, String password) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("Account with email "+ email+" does not exist");
        }

        UserEntity userEntityObj = optionalUser.get();

        if(!userEntityObj.getPassword().equals(password)){
            throw new UserNotFoundException("Password "+ password+ " is incorrect");
        }

        return userEntityObj;
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity, int id) throws UserNotFoundException {
        UserEntity updatedUserEntity = getById(id);

        updatedUserEntity.setUsername(userEntity.getUsername());
        updatedUserEntity.setChats(userEntity.getChats());
        updatedUserEntity.setProfilePictureUrl(userEntity.getProfilePictureUrl());
        updatedUserEntity.setFriends(userEntity.getFriends());
        updatedUserEntity.setPassword(userEntity.getPassword());
        updatedUserEntity.setSentFriendRequests(userEntity.getSentFriendRequests());
        updatedUserEntity.setReceivedFriendRequests(userEntity.getReceivedFriendRequests());

        return userRepo.save(updatedUserEntity);

    }

    @Override
    public Boolean deleteUser(int id) throws UserNotFoundException {
        UserEntity userEntity = getById(id);
        userEntity.setActive(false);
        userEntity.setUsername("DeletedUser#"+ userEntity.getId());
        userRepo.save(userEntity);
        return true;
    }

    @Override
    public UserEntity getById(int id) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("Account with id "+id+" does not exist");
        }
        return optionalUser.get();
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepo.findAll();
    }

}
