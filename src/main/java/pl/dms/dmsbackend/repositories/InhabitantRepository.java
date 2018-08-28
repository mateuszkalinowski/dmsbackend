package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.Inhabitant;

public interface InhabitantRepository extends JpaRepository<Inhabitant, Long> {
    Inhabitant findTopByEmail(String mail);
}
