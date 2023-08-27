package kdkd.youre.backend.domain.category.domain.repository;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        Boolean existsByNameAndMember(String name, Member member);
}
