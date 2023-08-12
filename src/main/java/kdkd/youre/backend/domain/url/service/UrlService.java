package kdkd.youre.backend.domain.url.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.member.domain.Member;
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

    public void deleteUrl(Long urlId, Member member) {

        Url url = urlRepository.findById(urlId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_URL));

        ValidateUrlOwnerShip(url, member);
        this.urlRepository.deleteById(urlId);
    }
    public void ValidateUrlOwnerShip(Url url, Member member) {
        if(!url.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }
}
