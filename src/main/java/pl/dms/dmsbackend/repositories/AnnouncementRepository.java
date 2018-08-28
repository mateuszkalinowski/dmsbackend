package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
