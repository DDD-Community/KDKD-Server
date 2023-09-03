package kdkd.youre.backend.domain.category.domain.repository;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustomRepository{
    Boolean existsByNameAndMember(String name, Member member);
    List<Category> findAllByMember(Member member);
}
