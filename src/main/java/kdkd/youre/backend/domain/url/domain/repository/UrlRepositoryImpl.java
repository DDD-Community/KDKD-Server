package kdkd.youre.backend.domain.url.domain.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static kdkd.youre.backend.domain.category.domain.QCategory.category;
import static kdkd.youre.backend.domain.tag.domain.QTag.tag;
import static kdkd.youre.backend.domain.url.domain.QUrl.url;

@RequiredArgsConstructor
public class UrlRepositoryImpl implements UrlCustomRepository {

    private final JPAQueryFactory queryFactory;

    private BooleanExpression categoryIdExpression(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return url.category.id.eq(categoryId);
    }

    private BooleanExpression isWatchedBooleanExpression(Boolean isWatched) {
        if (isWatched != null) {
            return url.isWatchedLater.eq(isWatched);
        }
        return null;
    }

    private OrderSpecifier<?> createOrderSpecifier(String order) {
        if (order.equals("asc")) return url.createdAt.asc();
        return url.createdAt.desc();
    }

    @Override
    public List<Url> findBySearchWord(Member member, UrlFindAllParam params, Pageable pageable) {

        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(params.getOrder());
        BooleanExpression isWatchedExpression = isWatchedBooleanExpression(params.getIsWatched());
        BooleanExpression categoryIdExpression = categoryIdExpression(params.getCategoryId());

        JPAQuery<Url> query = queryFactory
                .selectDistinct(url)
                .from(url)
                .join(tag)
                .on(url.id.eq(tag.url.id))
                .join(category)
                .on(url.category.id.eq(category.id))
                .join(category.member)
                .on(category.member.eq(member))
                .where(url.name.like("%" + params.getUrlKeyword() + "%")
                        .or(tag.name.like("%" + params.getUrlKeyword() + "%"))
                        .or(url.memo.like("%" + params.getUrlKeyword() + "%"))
                        .and(category.member.eq(member)));

        if (categoryIdExpression != null) {
            query = query.where(categoryIdExpression);
        }

        if (isWatchedExpression != null) {
            query = query.where(isWatchedExpression);
        }

        return query
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
