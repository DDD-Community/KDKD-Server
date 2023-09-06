package kdkd.youre.backend.domain.tag.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kdkd.youre.backend.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kdkd.youre.backend.domain.tag.domain.QTag.tag;

@RequiredArgsConstructor
public class TagRepositoryImpl implements TagCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findDistinctNameByMember(Member member) {

        return queryFactory
                .selectDistinct(tag.name)
                .from(tag)
                .where(tag.member.eq(member))
                .fetch();
    }
}
