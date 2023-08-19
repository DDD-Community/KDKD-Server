package kdkd.youre.backend.domain.category.domain.repository;

import kdkd.youre.backend.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        Category findByName(String name);
}
