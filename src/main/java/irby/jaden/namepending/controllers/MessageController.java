package irby.jaden.namepending.controllers;

import irby.jaden.namepending.models.Message;
import irby.jaden.namepending.serivce.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/topic/send/{chatId}/{topicId}")
    public Message sendToChatTopic(@DestinationVariable int chatId, @DestinationVariable int topicId, @Payload Message message) {
        Message createdMessage = messageService.create(message);
        messagingTemplate.convertAndSend("/topic/"+chatId+"/"+topicId);
        return createdMessage;
    }
}
