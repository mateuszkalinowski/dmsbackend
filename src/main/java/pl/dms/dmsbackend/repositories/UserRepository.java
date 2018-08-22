package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
