package kdkd.youre.backend.domain.url.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlAddressCheckResponse;
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

    @Transactional(readOnly = true)
    public UrlAddressCheckResponse checkUrlAddress(String address) {

        boolean isDuplicated = urlRepository.existsByUrlAddress(address);

        return UrlAddressCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }

    public IdResponse saveUrl(UrlSaveRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGROY));

        Url url = Url.builder()
                .urlAddress(request.getUrlAddress())
                .name(request.getName())
                .thumbnail(request.getThumbnail())
                .memo(request.getMemo())
                .isWatchedLater(request.getIsWatcedLater())
                .category(category)
                .build();

        urlRepository.save(url);

        // TODO: 태그 저장 필요

        IdResponse response = IdResponse.builder()
                .id(url.getId())
                .build();

        return response;
    }

    public void deleteUrl(Long urlId, Member member) {

        Url url = urlRepository.findById(urlId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_URL));

        validateUrlOwnerShip(url, member);
        this.urlRepository.deleteById(urlId);
    }

    public UrlFindResponse findUrl(Long urlId, Member member) {

        Url url = urlRepository.findById(urlId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_URL));
        validateUrlOwnerShip(url, member);

        return UrlFindResponse.builder()
                .url(url.getUrl())
                .tag(url.getTag())
                .name(url.getTitle())
                .build();
    }

    public void validateUrlOwnerShip(Url url, Member member) {
        if (!url.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }
}
