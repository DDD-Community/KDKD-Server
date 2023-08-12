package kdkd.youre.backend.domain.url.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlCheckResponse;
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
    public UrlCheckResponse checkUrl(String url) {

        boolean urlCheck = urlRepository.existsByUrl(url);

        UrlCheckResponse response = UrlCheckResponse.builder()
                .urlCheck(urlCheck)
                .build();

        return response;
    }

    public IdResponse saveUrl(UrlSaveRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGROY));

        Url url = Url.builder()
                .url(request.getUrl())
                .name(request.getName())
                .category(category)
                .build();
        urlRepository.save(url);

        IdResponse response = IdResponse.builder()
                .id(url.getId())
                .build();

        return response;
    }
}
