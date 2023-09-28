package irby.jaden.namepending.Controller;

import irby.jaden.namepending.Execeptions.UserAlreadyExistsException;
import irby.jaden.namepending.Execeptions.UserNotFoundException;
import irby.jaden.namepending.controllers.UserController;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.serivce.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest extends BaseControllerTest{

    @MockBean
    private UserService mockUserService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    private UserEntity inputUserEntity;
    private UserEntity responseUserEntity1;
    private UserEntity responseUserEntity2;

    @BeforeEach
    public void setUp(){
        inputUserEntity = new UserEntity("Bob@fake.com", "Bob", "BobTheGreatest","fakeURL.png");
        inputUserEntity.setId(1);
        responseUserEntity1 = new UserEntity();
        responseUserEntity1.setId(2);
        responseUserEntity2 = new UserEntity();
        responseUserEntity2.setId(3);
    }

    @Test
    public void registerUserSuccess() throws Exception {
        BDDMockito.doReturn(responseUserEntity1)
                .when(mockUserService).registerUser(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(inputUserEntity)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void registerUserFail() throws Exception {
        BDDMockito.doThrow(new UserAlreadyExistsException("Account with username "+ inputUserEntity.getUsername()+" exists"))
                .when(mockUserService).registerUser(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputUserEntity)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void updateUserSuccess() throws Exception {
        BDDMockito.doReturn(responseUserEntity1)
                .when(mockUserService).updateUser(inputUserEntity, 1);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputUserEntity)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateUserFail() throws Exception {
        BDDMockito.doThrow(new UserNotFoundException("Account with id 1 does not exist"))
                .when(mockUserService).updateUser(inputUserEntity, 1);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputUserEntity)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void loginUserSuccess() throws Exception {
        BDDMockito.doReturn(responseUserEntity1)
                .when(mockUserService).loginUser(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email","bob@fake.com")
                        .param("password","fakePass"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void loginUserFail() throws Exception {
        BDDMockito.doThrow(new UserNotFoundException("Password fakePass is incorrect"))
                .when(mockUserService).loginUser(any(), any());
        mockMvc.perform(MockMvcRequestBuilders.get("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email","bob@fake.com")
                        .param("password","fakePass"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getAllSuccess() throws Exception {
        List<UserEntity> list = new ArrayList<>();
        list.add(responseUserEntity1);
        list.add(responseUserEntity2);

        BDDMockito.doReturn(list)
                .when(mockUserService).getAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getByIdSuccess() throws Exception {
        BDDMockito.doReturn(responseUserEntity1)
                .when(mockUserService).getById(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getByIdFail() throws Exception {
        BDDMockito.doThrow(new UserNotFoundException("Account with id 1 does not exist"))
                .when(mockUserService).getById(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteSuccess() throws Exception {
        BDDMockito.doReturn(true)
                .when(mockUserService).deleteUser(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}",1)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deleteFail() throws Exception {
        BDDMockito.doThrow(new UserNotFoundException("Account with id 1 does not exist"))
                .when(mockUserService).deleteUser(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
