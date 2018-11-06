package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.UserRole;

public interface UserRolesRepository extends JpaRepository<UserRole, Long> {
;
}
