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

	private BooleanExpression urlKeywordExpression(String urlKeyword, String keywordRange) {  // TODO: 추후 keywordRange를 String 배열로 교체
		if (urlKeyword == null) {
			return null;
		}
		if (keywordRange == null) {
			return urlNameExpression(urlKeyword)
				.or(urlMemoExpression(urlKeyword))
				.or(tagNameExpression(urlKeyword));
		}
		if (keywordRange.equals("name")) { // TODO: 추후 enum으로 교체
			return urlNameExpression(urlKeyword);
		}
		if (keywordRange.equals("memo")) {
			return urlMemoExpression(urlKeyword);
		}
		if (keywordRange.equals("tag")) {
			return tagNameExpression(urlKeyword);
		}
		return urlNameExpression(urlKeyword)
			.or(urlMemoExpression(urlKeyword))
			.or(tagNameExpression(urlKeyword)); // TODO: 깔끔하게 수정 필요...
	}

	private BooleanExpression urlNameExpression(String urlName) {
		return url.name.like("%" + urlName + "%");
	}

	private BooleanExpression urlMemoExpression(String urlMemo) {
		return url.name.like("%" + urlMemo + "%");
	}

	private BooleanExpression tagNameExpression(String tagName) {
		return tag.name.like("%" + tagName + "%");
	}

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
		if (order.equals("asc"))
			return url.createdAt.asc();
		return url.createdAt.desc();
	}

	@Override
	public List<Url> findBySearchWord(Member member, UrlFindAllParam params, Pageable pageable) {

		OrderSpecifier<?> orderSpecifier = createOrderSpecifier(params.getOrder());
		BooleanExpression urlKeywordExpression = urlKeywordExpression(params.getUrlKeyword(), params.getKeywordRange());
		BooleanExpression categoryIdExpression = categoryIdExpression(params.getCategoryId());
		BooleanExpression isWatchedExpression = isWatchedBooleanExpression(params.getIsWatched());

		JPAQuery<Url> query = queryFactory
			.selectDistinct(url)
			.from(url)
			.join(tag)
			.on(url.id.eq(tag.url.id))
			.join(category)
			.on(url.category.id.eq(category.id))
			.join(category.member)
			.on(category.member.eq(member))
			.where(category.member.eq(member));

		if (urlKeywordExpression != null) {
			query = query.where(urlKeywordExpression);
		}

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
