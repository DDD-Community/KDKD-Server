package kdkd.youre.backend.domain.url.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.category.presentation.dto.response.CategoryDto;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.tag.service.TagService;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlAddressCheckResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
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
    private final TagService tagService;


    @Transactional(readOnly = true)
    public UrlAddressCheckResponse checkUrlAddress(String address) {

        boolean isDuplicated = urlRepository.existsByUrlAddress(address);

        return UrlAddressCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }

    public IdResponse saveUrl(UrlSaveRequest request, Member member) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGROY));

        validateCategoryOwnerShip(category, member);

        Url url = Url.builder()
                .urlAddress(request.getUrlAddress())
                .name(request.getName())
                .thumbnail(request.getThumbnail())
                .memo(request.getMemo())
                .isWatchedLater(request.getIsWatcedLater())
                .category(category)
                .build();

        urlRepository.save(url);
        tagService.saveTagList(request.getTag(), url, member);

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
                .urlAddress(url.getUrlAddress())
                .name(url.getName())
                .thumbnail(url.getThumbnail())
                .category(CategoryDto.from(url.getCategory()))
                .memo(url.getMemo())
                .isWatchedLater(url.getIsWatchedLater())
                .build();
    }

    public void validateUrlOwnerShip(Url url, Member member) {
        if (!url.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }

    public void validateCategoryOwnerShip(Category category, Member member) { // TODO: 위치 혹은 이름 더 적절하게 변경하기
        if(!category.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }
}
