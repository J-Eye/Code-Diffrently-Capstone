package irby.jaden.namepending.Controller;


import irby.jaden.namepending.controllers.MessageController;
import irby.jaden.namepending.models.Message;
import irby.jaden.namepending.serivce.MessageService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private MessageController messageController;

    private Message inputMessage;

    @BeforeEach
    public void setUp() {
        messageController = new MessageController(messageService, messagingTemplate);
         inputMessage = new Message();
        inputMessage.setContent("Hello, world!");
    }

    @Test
    public void testSendToChatTopic() {
        BDDMockito.doReturn(inputMessage)
                .when(messageService).create(ArgumentMatchers.any());
        // Invoke the sendToChatTopic() method
        messageController.sendToChatTopic(123, 456, inputMessage);

        // Verify that the message was sent to the correct chat topic
        verify(messagingTemplate).convertAndSend("/topic/123/456", inputMessage);
    }
}
