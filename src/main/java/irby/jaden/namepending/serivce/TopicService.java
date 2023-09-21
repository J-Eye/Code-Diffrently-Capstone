package irby.jaden.namepending.serivce;

import irby.jaden.namepending.Execeptions.TopicException;
import irby.jaden.namepending.models.Topic;

import java.util.List;

public interface TopicService {
    Topic create(Topic topic);
    Topic update(Topic topic, int id);
    Topic getById(int id) throws TopicException;
    List<Topic> getAll();
    Boolean delete(int id);
}
