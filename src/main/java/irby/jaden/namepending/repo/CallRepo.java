package irby.jaden.namepending.repo;

import irby.jaden.namepending.models.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CallRepo extends JpaRepository<Call,Integer> {
}
