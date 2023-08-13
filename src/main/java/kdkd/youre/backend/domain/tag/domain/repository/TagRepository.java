package kdkd.youre.backend.domain.tag.domain.repository;

import kdkd.youre.backend.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
