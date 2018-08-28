package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.Request;

public interface RequestRepository extends JpaRepository<Request,Long> {
}
