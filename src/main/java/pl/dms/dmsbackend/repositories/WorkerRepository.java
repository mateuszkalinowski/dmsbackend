package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.users.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Long> {
    Worker findByEmail(String email);
}
