package kdkd.youre.backend.domain.url.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kdkd.youre.backend.domain.tag.domain.QTag.tag;
import static kdkd.youre.backend.domain.url.domain.QUrl.url;

@Repository
@RequiredArgsConstructor
public class UrlRepositoryImpl implements UrlCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Url> findBySearchWord(UrlFindAllParam params) {

        return queryFactory
                .selectDistinct(url)
                .from(url)
                .join(tag)
                .on(url.id.eq(tag.url.id))
                .where(url.name.like("%" + params.getUrlKeyword() + "%")
                        .or(tag.name.like("%" + params.getUrlKeyword() + "%")))
                .fetch();
    }
}
