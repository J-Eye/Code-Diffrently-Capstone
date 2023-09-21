package irby.jaden.namepending.repo;

import irby.jaden.namepending.models.Call;
import irby.jaden.namepending.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<Message,Integer > {
}
