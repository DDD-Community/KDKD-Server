package kdkd.youre.backend.domain.url.domain.repository;

import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;

import java.util.List;

public interface UrlCustomRepository {
    List<Url> findBySearchWord(UrlFindAllParam params);
}