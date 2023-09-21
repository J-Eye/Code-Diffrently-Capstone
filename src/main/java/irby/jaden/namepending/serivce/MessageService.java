package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.CallException;
import irby.jaden.namepending.models.Message;

import java.util.List;

public interface MessageService {
    Message create(Message message);
    Message update(String content, int id) throws CallException.MessageException;
    Message getById(int id) throws CallException.MessageException;
    List<Message> getAll();
}
