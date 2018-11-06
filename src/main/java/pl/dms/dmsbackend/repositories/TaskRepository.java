package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
