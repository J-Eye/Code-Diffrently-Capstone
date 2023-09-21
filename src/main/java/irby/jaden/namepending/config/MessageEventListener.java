package irby.jaden.namepending.config;

/*import irby.jaden.namepending.models.Message;
import irby.jaden.namepending.models.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class MessageEventListener {

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleChatAppConnectListener(SessionConnectEvent event){
        log.info("Received a new web socket connection : "+event.getSource());
    }

    @EventListener
    public void handleChatAppDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        UserEntity userEntity = (UserEntity) headerAccessor.getSessionAttributes().get("username");

        if (userEntity != null){

            Message chatMessage = new Message();
            chatMessage.setType("leave");
            chatMessage.setSender(userEntity);

            messageSendingOperations.convertAndSend("/topic/public", chatMessage);
        }

    }


}*/
public class MessageEventListener {
}
