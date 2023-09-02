package kdkd.youre.backend.domain.category.domain.repository;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.member.domain.Member;

public interface CategoryCustomRepository {
    Long findMaxPositionByMember(Member member);
    Long findMaxPositionForMemberAndParent(Member member, Category parent);
}
