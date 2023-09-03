package kdkd.youre.backend.domain.url.domain.repository;

import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UrlCustomRepository {
    List<Url> findBySearchWord(Member member, UrlFindAllParam params, Pageable pageable);
}
