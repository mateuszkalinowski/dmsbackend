package pl.dms.dmsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dms.dmsbackend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getTopByCategory(String category);
}
