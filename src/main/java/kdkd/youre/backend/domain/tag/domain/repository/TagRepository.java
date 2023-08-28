package kdkd.youre.backend.domain.tag.domain.repository;

import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.tag.domain.Tag;
import kdkd.youre.backend.domain.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByMemberAndUrl(Member member, Url url);

    List<Tag> findTagsByUrlId(Long id);
}
