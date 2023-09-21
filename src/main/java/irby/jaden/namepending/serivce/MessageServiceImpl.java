package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.CallException;
import irby.jaden.namepending.models.Message;
import irby.jaden.namepending.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepo messageRepo;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public Message create(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public Message update(String content, int id) throws CallException.MessageException {
        Message updateMessage = getById(id);
        updateMessage.setContent(content);

        return messageRepo.save(updateMessage);
    }

    @Override
    public Message getById(int id) throws CallException.MessageException {
        Optional<Message> message = messageRepo.findById(id);
        if(message.isEmpty()){
            throw new CallException.MessageException("Could not find message at id "+id);
        }
        return message.get();
    }

    @Override
    public List<Message> getAll() {
        return messageRepo.findAll();
    }
}
