package irby.jaden.namepending.Service;

import irby.jaden.namepending.Execeptions.ChatException;
import irby.jaden.namepending.models.Chat;
import irby.jaden.namepending.repo.ChatRepo;
import irby.jaden.namepending.serivce.ChatServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ChatServiceImplTest {

    @MockBean
    private ChatRepo mockChatRepo;

    @Autowired
    private ChatServiceImpl chatService;

    private Chat inputChat;
    private Chat responseChat1;
    private Chat responseChat2;

    @BeforeEach
    public void setUp() {
        inputChat = new Chat("2389101", "input");
        responseChat1 = new Chat();
        responseChat2 = new Chat();
    }

    @Test
    public void createChatSuccess() {
        BDDMockito.doReturn(responseChat1).when(mockChatRepo).save(ArgumentMatchers.any());
        Chat createdChat = chatService.create(inputChat);
        Assertions.assertNotNull(createdChat, "Chat should not be null");
        Assertions.assertEquals(responseChat1.toString(), createdChat.toString());
    }

    @Test
    public void updateChatSuccess() throws ChatException {
        BDDMockito.doReturn(Optional.of(responseChat1)).when(mockChatRepo).findById(ArgumentMatchers.any());
        BDDMockito.doReturn(responseChat1).when(mockChatRepo).save(ArgumentMatchers.any());

        Chat updatedChat = new Chat();
        updatedChat.setName("Updated Chat");

        Chat resultChat = chatService.update(updatedChat, 1);

        Assertions.assertNotNull(resultChat, "Chat should not be null");
        Assertions.assertEquals("Updated Chat", resultChat.getName());
    }

    @Test
    public void updateChatChatNotFound() {
        BDDMockito.doReturn(Optional.empty()).when(mockChatRepo).findById(ArgumentMatchers.any());

        Chat updatedChat = new Chat();
        updatedChat.setName("Updated Chat");

        Assertions.assertThrows(ChatException.class, () -> chatService.update(updatedChat, 1));
    }

    @Test
    public void deleteChatSuccess() throws ChatException {
        BDDMockito.doReturn(Optional.of(responseChat1)).when(mockChatRepo).findById(ArgumentMatchers.any());
        BDDMockito.doReturn(responseChat1).when(mockChatRepo).save(ArgumentMatchers.any());

        boolean result = chatService.delete(1);

        Assertions.assertTrue(result, "Delete should return true");
        Assertions.assertFalse(responseChat1.isActive(), "Chat should be deactivated");
    }

    @Test
    public void deleteChatChatNotFound() {
        BDDMockito.doReturn(Optional.empty()).when(mockChatRepo).findById(ArgumentMatchers.any());

        Assertions.assertThrows(ChatException.class, () -> chatService.delete(1));
    }

    @Test
    public void getChatByIdSuccess() throws ChatException {
        BDDMockito.doReturn(Optional.of(responseChat1)).when(mockChatRepo).findById(ArgumentMatchers.any());

        Chat retrievedChat = chatService.getById(1);

        Assertions.assertNotNull(retrievedChat, "Chat should not be null");
        Assertions.assertEquals(responseChat1.getId(), retrievedChat.getId());
    }

    @Test
    public void getChatByIdChatNotFound() {
        BDDMockito.doReturn(Optional.empty()).when(mockChatRepo).findById(ArgumentMatchers.any());

        Assertions.assertThrows(ChatException.class, () -> chatService.getById(1));
    }

    @Test
    public void getAllChatsSuccess() {
        List<Chat> chats = new ArrayList<>();
        chats.add(responseChat1);
        chats.add(responseChat2);

        BDDMockito.doReturn(chats).when(mockChatRepo).findAll();
        List<Chat> retrievedChats = chatService.getAll();

        Assertions.assertNotNull(retrievedChats, "Chats should not be null");
        Assertions.assertIterableEquals(chats, retrievedChats);
    }
}

