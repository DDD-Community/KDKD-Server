package kdkd.youre.backend.domain.tag.domain.repository;

import kdkd.youre.backend.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByUrl_Id(Long urlId);
}
