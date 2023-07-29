package kdkd.youre.backend.domain.url.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlSaveResponse;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UrlService {

    private final UrlRepository urlRepository;
    private final CategoryRepository categoryRepository;


    // 1. url 중복체크
    @Transactional(readOnly = true)
    public UrlFindResponse checkUrlDuplication(String url) {

        boolean urlDuplicate = urlRepository.existsByUrl(url);

//        if (urlRepository.existsByUrl(url)) {
//            throw new CustomException(ErrorCode.EXIST_USER_EMAIL);
//        }

        UrlFindResponse response = UrlFindResponse.builder()
                .urlCheck(urlDuplicate)
                .build();

        return response;
    }

    // 2. url 저장
    public UrlSaveResponse add(UrlRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGROY));

        Url url = Url.builder()
                .url(request.getUrl())
                .title(request.getTitle())
                .category(category)
                .build();
        urlRepository.save(url);

        UrlSaveResponse response = UrlSaveResponse.builder()
                .urlId(url.getId())
                .build();

        return response;
    }

}
