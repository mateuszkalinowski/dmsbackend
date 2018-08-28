package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.Inhabitant;
import pl.dms.dmsbackend.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Long> {
    Worker findTopByEmail(String mail);
}
