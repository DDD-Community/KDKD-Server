package kdkd.youre.backend.domain.category.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryPositionUpdateRequest;
import kdkd.youre.backend.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kdkd.youre.backend.domain.category.domain.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long findMaxPositionByMember(Member member) {
        return queryFactory
                .select(category.position.max())
                .from(category)
                .where(
                        category.member.eq(member).and(category.parent.isNull())
                )
                .fetchOne();
    }

    @Override
    public Long findMaxPositionForMemberAndParent(Member member, Category parent) {
        return queryFactory
                .select(category.position.max())
                .from(category)
                .where(
                        category.member.eq(member).and(category.parent.eq(parent))
                )
                .fetchOne();
    }

    @Override
    public Long findCurrentPosition(Member member, CategoryPositionUpdateRequest request) {

        if (request.getParentId() == null) {
            return queryFactory.select(category.position)
                    .from(category)
                    .where(category.id.eq(request.getAboveTargetId())
                            .and(category.parent.isNull())
                            .and(category.member.eq(member)))
                    .fetchOne();
        }
        return queryFactory.select(category.position)
                .from(category)
                .where(category.id.eq(request.getAboveTargetId())
                        .and(category.parent.id.eq(request.getParentId()))
                        .and(category.member.eq(member)))
                .fetchOne();
    }

    @Override
    public List<Long> findNextPosition(Member member, CategoryPositionUpdateRequest request, Long current) {
        if (request.getParentId() == null) {
            return queryFactory.select(category.position)
                    .from(category)
                    .where(category.parent.isNull()
                            .and(category.position.gt(current))
                            .and(category.member.eq(member)))
                    .orderBy(category.position.asc())
                    .fetch();

        }
        return queryFactory.select(category.position)
                .from(category)
                .where(category.parent.id.eq(request.getParentId())
                        .and(category.position.gt(current))
                        .and(category.member.eq(member)))
                .orderBy(category.position.asc())
                .fetch();
    }

    @Override
    public Long findDepth(Member member, CategoryPositionUpdateRequest request) {

        if (request.getParentId() == null) {
            return 1L;
        }

        if (request.getAboveTargetId() == null) {
            return queryFactory.select(category.depth)
                    .from(category)
                    .where(category.id.eq(request.getParentId())
                            .and(category.member.eq(member)))
                    .fetchOne();
        }

        return queryFactory.select(category.depth)
                .from(category)
                .where(category.parent.id.eq(request.getParentId())
                        .and(category.id.eq(request.getAboveTargetId()))
                        .and(category.member.eq(member)))
                .fetchOne();
    }
}
