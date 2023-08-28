package kdkd.youre.backend.domain.url.domain.repository;

import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UrlRepositoryImpl implements UrlCustomRepository{

    @Override
    public List<Url> findBySearchWord(UrlFindAllParam params) {
        return null;

    }
}
