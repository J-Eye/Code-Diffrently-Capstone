package irby.jaden.namepending.Service;

import irby.jaden.namepending.Execeptions.CallException.MessageException;
import irby.jaden.namepending.models.Message;
import irby.jaden.namepending.models.UserEntity;
import irby.jaden.namepending.repo.MessageRepo;
import irby.jaden.namepending.serivce.MessageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MessageServiceImplTest {

    @MockBean
    private MessageRepo messageRepo;

    @Autowired
    private MessageServiceImpl messageService;

    private Message inputMessage;
    private Message responseMessage1;
    private Message responseMessage2;

    @BeforeEach
    public void setUp() {
        inputMessage = new Message("Example msg", new UserEntity(), "SEND");
        inputMessage.setId(1);
        responseMessage1 = new Message();
        responseMessage1.setId(2);
        responseMessage2 = new Message();
        responseMessage2.setId(3);
    }

    @Test
    public void createMessageSuccess() {
        BDDMockito.doReturn(inputMessage).when(messageRepo).save(ArgumentMatchers.any());
        Message createdMessage = messageService.create(inputMessage);
        Assertions.assertNotNull(createdMessage, "Message should not be null");
        Assertions.assertEquals(inputMessage.toString(), createdMessage.toString());
    }

    @Test
    public void updateMessageSuccess() throws MessageException {
        BDDMockito.doReturn(Optional.of(responseMessage1)).when(messageRepo).findById(ArgumentMatchers.any());
        BDDMockito.doReturn(inputMessage).when(messageRepo).save(ArgumentMatchers.any());
        Message updatedMessage = messageService.update("This is the updated message.", 1);
        Assertions.assertNotNull(updatedMessage, "Message should not be null");
        Assertions.assertEquals(inputMessage.toString(), updatedMessage.toString());
    }

    @Test
    public void updateMessageFail() {
        BDDMockito.doReturn(Optional.empty()).when(messageRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(MessageException.class, () -> {
            messageService.update("This is the updated message.", 1);
        });
    }

    @Test
    public void getMessageByIdSuccess() throws MessageException {
        BDDMockito.doReturn(Optional.of(responseMessage1)).when(messageRepo).findById(ArgumentMatchers.any());
        Message foundMessage = messageService.getById(1);
        Assertions.assertNotNull(foundMessage, "Message should not be null");
        Assertions.assertEquals(responseMessage1.toString(), foundMessage.toString());
    }

    @Test
    public void getMessageByIdFail() {
        BDDMockito.doReturn(Optional.empty()).when(messageRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(MessageException.class, () -> {
            messageService.getById(1);
        });
    }

    @Test
    public void getAllMessagesSuccess() {
        List<Message> messages = new ArrayList<>();
        messages.add(responseMessage1);
        messages.add(responseMessage2);
        BDDMockito.doReturn(messages).when(messageRepo).findAll();
        List<Message> foundMessages = messageService.getAll();
        Assertions.assertNotNull(foundMessages, "Messages should not be null");
        Assertions.assertIterableEquals(messages, foundMessages);
    }
}
