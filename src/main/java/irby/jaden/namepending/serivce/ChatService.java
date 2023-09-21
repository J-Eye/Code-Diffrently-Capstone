package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.ChatException;
import irby.jaden.namepending.models.Chat;

import java.util.List;

public interface ChatService {
    Chat create(Chat chat);
    Chat update(Chat chat, int id) throws ChatException;
    Boolean delete(int id) throws ChatException;
    Chat getById(int id) throws ChatException;
    List<Chat> getAll();
}
