package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.TopicException;
import irby.jaden.namepending.models.Topic;
import irby.jaden.namepending.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService{


    private final TopicRepo topicRepo;

    @Autowired
    public TopicServiceImpl(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    @Override
    public Topic create(Topic topic) {
        return topicRepo.save(topic);
    }

    @Override
    public Topic update(Topic topic, int id) {
        return null;
    }

    @Override
    public Topic getById(int id) throws TopicException {
        Optional<Topic> topic = topicRepo.findById(id);
        if(topic.isEmpty()){
            throw new TopicException("Topic not found at id"+ id);
        }
        return topic.get();
    }

    @Override
    public List<Topic> getAll() {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }
}
