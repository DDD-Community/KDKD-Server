package kdkd.youre.backend.domain.url.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.tag.domain.Tag;
import kdkd.youre.backend.domain.tag.domain.repository.TagRepository;
import kdkd.youre.backend.domain.tag.service.TagService;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.UrlCrawlingDto;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlUpdateRequest;
import kdkd.youre.backend.domain.url.presentation.dto.UrlDto;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindAllResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
import kdkd.youre.backend.domain.url.service.helper.JsoupCrawlingHelper;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UrlService {

    private final UrlRepository urlRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final JsoupCrawlingHelper jsoupCrawlingHelper;

    public IdResponse saveUrl(UrlSaveRequest request, Member member) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

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
        tagService.saveAllTag(request.getTag(), url, member);

        IdResponse response = IdResponse.builder()
                .id(url.getId())
                .build();

        return response;
    }

    public void updateUrl(Long urlId, UrlUpdateRequest request, Member member) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

        validateCategoryOwnerShip(category, member);

        Url url = urlRepository.findById(urlId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_URL));

        url.updateUrl(request, category);

        tagService.deleteAllTag(url);
        tagService.saveAllTag(request.getTag(), url, member);
    }

    public void deleteUrl(Long urlId, Member member) {

        Url url = urlRepository.findById(urlId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_URL));

        validateUrlOwnerShip(url, member);
        urlRepository.delete(url);
    }

    public UrlFindResponse findUrl(String address, Member member) {

        boolean isDuplicated = urlRepository.existsByUrlAddressAndCategory_Member(address, member);

        UrlFindResponse.UrlFindResponseBuilder builder = UrlFindResponse.builder();

        if (!isDuplicated) {
            UrlCrawlingDto urlCrawlingDto = jsoupCrawlingHelper.getUrlCrawlingInfo(address);

            return builder
                    .isSaved(false)
                    .urlAddress(urlCrawlingDto.getAddress())
                    .name(urlCrawlingDto.getName())
                    .thumbnail(urlCrawlingDto.getThumbnail())
                    .tag(Collections.emptyList())
                    .isWatchedLater(false)
                    .build();
        }
        Url url = Optional.ofNullable(urlRepository.findByUrlAddressAndCategory_Member(address, member))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_URL));

        List<Tag> tags = tagRepository.findByMemberAndUrl(member, url);
        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        builder
                .urlId(url.getId())
                .isSaved(true)
                .urlAddress(url.getUrlAddress())
                .name(url.getName())
                .thumbnail(url.getThumbnail())
                .categoryId(url.getCategory().getId())
                .tag(tagNames)
                .memo(url.getMemo())
                .isWatchedLater(url.getIsWatchedLater());

        return builder.build();
    }

    public UrlFindAllResponse findAllUrl(UrlFindAllParam params, Pageable pageable) {

        List<Url> urls = urlRepository.findBySearchWord(params, pageable);

        List<UrlDto> urlDtos = urls.stream()
                .map(url -> {
                    List<Tag> tags = tagRepository.findByUrl(url);
                    List<String> tagNames = tags.stream()
                            .map(Tag::getName)
                            .collect(Collectors.toList());
                    return UrlDto.from(url, tagNames);
                })
                .collect(Collectors.toList());

        return UrlFindAllResponse.builder()
                .totalCount(urlDtos.size())
                .url(urlDtos)
                .build();
    }

    public void validateUrlOwnerShip(Url url, Member member) {

        if (!url.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }

    public void validateCategoryOwnerShip(Category category, Member member) { // TODO: 위치 혹은 이름 더 적절하게 변경하기
        if (!category.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }
}
