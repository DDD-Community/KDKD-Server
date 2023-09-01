package kdkd.youre.backend.domain.category.domain.repository;

import kdkd.youre.backend.domain.member.domain.Member;

public interface CategoryCustomRepository {
    Long findMaxPositionByMember(Member member);
}
