package kdkd.youre.backend.domain.tag.domain.repository;

import kdkd.youre.backend.domain.member.domain.Member;

import java.util.List;

public interface TagCustomRepository {

    List<String> findDistinctNameByMember(Member member);
}
