package kdkd.youre.backend.domain.category.domain.repository;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryPositionUpdateRequest;
import kdkd.youre.backend.domain.member.domain.Member;

import java.util.List;

public interface CategoryCustomRepository {
    Long findMaxPositionByMember(Member member);
    Long findMaxPositionForMemberAndParent(Member member, Category parent);

    Long findCurrentPosition(Member member, CategoryPositionUpdateRequest request);

    List<Long> findNextPosition(Member member, CategoryPositionUpdateRequest request, Long current);

    Long findDepth(Member member, CategoryPositionUpdateRequest request);
}
