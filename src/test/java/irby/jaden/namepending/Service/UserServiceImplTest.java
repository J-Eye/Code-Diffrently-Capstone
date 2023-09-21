package irby.jaden.namepending.Service;

import irby.jaden.namepending.Execeptions.UserException;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.repo.UserRepo;
import irby.jaden.namepending.serivce.UserService;
import irby.jaden.namepending.serivce.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepo mockUserRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity inputUserEntity;
    private UserEntity responseUserEntity1;
    private UserEntity responseUserEntity2;

    @BeforeEach
    public void setUp() throws ParseException {
        inputUserEntity = new UserEntity("Bob@Fake.com","Bob", "securePassword","fakeadress.jpg");

        responseUserEntity1 = new UserEntity();
        responseUserEntity1.setId(1);
        responseUserEntity2 = new UserEntity();
        responseUserEntity2.setId(2);

    }

    @Test
    public void registerUserSuccess() throws UserException {
        BDDMockito.doReturn(responseUserEntity1).when(mockUserRepo).save(ArgumentMatchers.any());
        //BDDMockito.doReturn(null).when(mockUserRepo).findByUsername(ArgumentMatchers.any());

        UserEntity returnedUser = userService.registerUser(inputUserEntity);
        Assertions.assertNotNull(returnedUser, "User should not be null");
        Assertions.assertEquals(1,returnedUser.getId());

    }


}
