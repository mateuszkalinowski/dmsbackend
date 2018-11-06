package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.users.Inhabitant;

public interface InhabitantRepository extends JpaRepository<Inhabitant, Long> {
    Inhabitant findByEmail(String email);
}
