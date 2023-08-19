package kdkd.youre.backend.domain.tag.domain.repository;

import kdkd.youre.backend.domain.tag.domain.Tag;
import kdkd.youre.backend.domain.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByUrl(Url url);

    Tag findByUrlAndName(Url url, String name);
}
