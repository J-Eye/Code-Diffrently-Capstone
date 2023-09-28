package irby.jaden.namepending.Service;

import irby.jaden.namepending.Execeptions.TopicException;
import irby.jaden.namepending.models.Topic;
import irby.jaden.namepending.repo.TopicRepo;
import irby.jaden.namepending.serivce.TopicServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TopicServiceImplTest {

    @MockBean
    private TopicRepo topicRepo;

    @Autowired
    private TopicServiceImpl topicService;

    private Topic inputTopic;
    private Topic responseTopic1;
    private Topic responseTopic2;

    @BeforeEach
    public void setUp() {
        inputTopic = new Topic("number1");
        inputTopic.setId(1);
        responseTopic1 = new Topic("number2");
        responseTopic1.setId(2);
        responseTopic2 = new Topic("number3");
        responseTopic2.setId(3);
    }

    @Test
    public void createTopicSuccess() {
        BDDMockito.doReturn(inputTopic).when(topicRepo).save(ArgumentMatchers.any());
        Topic createdTopic = topicService.create(inputTopic);
        Assertions.assertNotNull(createdTopic, "Topic should not be null");
        Assertions.assertEquals(inputTopic.toString(), createdTopic.toString());
    }

    @Test
    public void getTopicByIdSuccess() throws TopicException {
        BDDMockito.doReturn(Optional.of(responseTopic1)).when(topicRepo).findById(ArgumentMatchers.any());
        Topic foundTopic = topicService.getById(1);
        Assertions.assertNotNull(foundTopic, "Topic should not be null");
        Assertions.assertEquals(responseTopic1.toString(), foundTopic.toString());
    }

    @Test
    public void getTopicByIdFail() {
        BDDMockito.doReturn(Optional.empty()).when(topicRepo).findById(1);
        Assertions.assertThrows(TopicException.class, () -> {
            topicService.getById(1);
        });
    }
}
