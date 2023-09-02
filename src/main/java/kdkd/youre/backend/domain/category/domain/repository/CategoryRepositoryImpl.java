package kdkd.youre.backend.domain.category.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kdkd.youre.backend.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;

import static kdkd.youre.backend.domain.category.domain.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Long findMaxPositionByMember(Member member) {

            return queryFactory
                    .select(category.position.max())
                    .from(category)
                    .where(category.member.eq(member))
                    .select(category.position.max())
                    .fetchOne();
    }

    @Override
    public Long findMaxPositionForMemberAndParent(Member member,Long parentId) {
        return queryFactory
                .select(category.position.max())
                .from(category)
                .where(
                        category.member.eq(member).and(category.parent.id.eq(parentId))
                )
                .fetchOne();
    }
}
