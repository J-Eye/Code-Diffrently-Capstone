package irby.jaden.namepending.repo;

import irby.jaden.namepending.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends JpaRepository<Chat,Integer > {

}
