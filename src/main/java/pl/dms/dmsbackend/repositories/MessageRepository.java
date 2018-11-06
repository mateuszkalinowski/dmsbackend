package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
