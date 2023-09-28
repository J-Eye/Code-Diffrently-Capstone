package irby.jaden.namepending.Service;

import irby.jaden.namepending.Execeptions.UserAlreadyExistsException;
import irby.jaden.namepending.Execeptions.UserNotFoundException;
import irby.jaden.namepending.models.Call;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.repo.UserRepo;
import irby.jaden.namepending.serivce.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepo mockUserRepo;

    @Autowired
    private UserServiceImpl userService;

    private UserEntity inputUserEntity;
    private UserEntity responseUserEntity1;
    private UserEntity responseUserEntity2;

    @BeforeEach
    public void setUp() throws ParseException {
        inputUserEntity = new UserEntity("Bob@Fake.com","Bob", "securePassword","fakeadress.jpg");
        inputUserEntity.setId(1);
        responseUserEntity1 = new UserEntity("John@Fake.com","Jmoney","pass","picture.jpg");
        responseUserEntity1.setId(2);
        responseUserEntity2 = new UserEntity();
        responseUserEntity2.setId(3);

    }

    @Test
    public void registerUserSuccess() throws UserNotFoundException, UserAlreadyExistsException {
        BDDMockito.doReturn(responseUserEntity1).when(mockUserRepo).save(ArgumentMatchers.any());
        UserEntity returnedUser = userService.registerUser(inputUserEntity);
        Assertions.assertNotNull(returnedUser, "User should not be null");
        Assertions.assertEquals(2,returnedUser.getId());
    }

    @Test
    public void registerUserFail(){
        BDDMockito.doReturn(Optional.of(responseUserEntity1)).when(mockUserRepo).findByUsername(ArgumentMatchers.any());
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(inputUserEntity);
        });
    }

    @Test
    public void loginUserSuccess() throws UserNotFoundException {
        BDDMockito.doReturn(Optional.of(responseUserEntity1)).when(mockUserRepo).findByEmail(ArgumentMatchers.any());
        UserEntity returnedUser = userService.loginUser(responseUserEntity1.getEmail(), responseUserEntity1.getPassword());
        Assertions.assertNotNull(returnedUser, "User should not be null");
        Assertions.assertEquals(2,returnedUser.getId());
    }

    @Test
    public void loginUserFail() throws UserNotFoundException {
        BDDMockito.doReturn(Optional.of(responseUserEntity1)).when(mockUserRepo).findByEmail(ArgumentMatchers.any());
        Assertions.assertThrows(UserNotFoundException.class, () ->  {
            userService.loginUser(responseUserEntity1.getEmail(),"Wrong password");
        });
    }

    @Test
    public void updateUserSuccess() throws UserNotFoundException {
        BDDMockito.doReturn(Optional.of(responseUserEntity1)).when(mockUserRepo).findById(ArgumentMatchers.any());
        BDDMockito.doReturn(inputUserEntity).when(mockUserRepo).save(ArgumentMatchers.any());

        UserEntity returnedUser = userService.updateUser(inputUserEntity, 1);

        Assertions.assertEquals(inputUserEntity.toString(), returnedUser.toString());
    }

    @Test
    public void getByIdSuccess() throws UserNotFoundException {
        BDDMockito.doReturn(Optional.of(responseUserEntity1)).when(mockUserRepo).findById(ArgumentMatchers.any());

        UserEntity returnedUser = userService.getById(1);

        Assertions.assertEquals(responseUserEntity1.toString(), returnedUser.toString());
    }
    @Test
    public void getByIdFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(ArgumentMatchers.any());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getById(1);
        });
    }

    @Test
    public void getAllUsersSuccess(){
        List<UserEntity> users = new ArrayList<>();
        users.add(responseUserEntity1);
        users.add(responseUserEntity1);

        BDDMockito.doReturn(users).when(mockUserRepo).findAll();
        List<UserEntity> returnedCals = userService.getAll();
        Assertions.assertNotNull(returnedCals, "Users should not be null");
        Assertions.assertIterableEquals(users, returnedCals);
    }

}
