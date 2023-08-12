package kdkd.youre.backend.domain.url.service;

import com.sun.tools.jconsole.JConsoleContext;
import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.member.domain.repository.MemberRepository;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlCheckResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlSaveResponse;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UrlService {

    private final UrlRepository urlRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public UrlCheckResponse checkUrl(String url) {

        boolean urlCheck = urlRepository.existsByUrl(url);

        UrlCheckResponse response = UrlCheckResponse.builder()
                .urlCheck(urlCheck)
                .build();

        return response;
    }

    public UrlSaveResponse saveUrl(UrlRequest request) {

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

    public void deleteUrl(Long urlId, Long userId) {

        Category category = urlRepository.findById(urlId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGROY)).getCategory();

        ValidateUrlOwnerShip(category, userId);
        this.urlRepository.deleteById(urlId);
    }
    public void ValidateUrlOwnerShip(Category category, Long userId) {
        if(!category.isPublishedBy(userId))
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
    }
}
