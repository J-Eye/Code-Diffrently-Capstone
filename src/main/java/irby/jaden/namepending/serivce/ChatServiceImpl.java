package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.ChatException;
import irby.jaden.namepending.models.Chat;
import irby.jaden.namepending.repo.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatRepo chatRepo;

    @Autowired
    public ChatServiceImpl(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    @Override
    public Chat create(Chat chat) {
        return chatRepo.save(chat);
    }

    @Override
    public Chat update(Chat chat, int id) throws ChatException {
        Chat updateChat = getById(id);
        updateChat.setName(chat.getName());

        return chatRepo.save(chat);
    }

    @Override
    public Boolean delete(int id) throws ChatException {
        Chat chat = getById(id);
        chat.setActive(false);
        chatRepo.save(chat);
        return true;
    }

    @Override
    public Chat getById(int id) throws ChatException {
        Optional<Chat> chat = chatRepo.findById(id);
        if(chat.isEmpty()){
            throw new ChatException("No Chat found at Id "+id);
        }
        return chat.get();

    }

    @Override
    public List<Chat> getAll() {
        return chatRepo.findAll();
    }
}
