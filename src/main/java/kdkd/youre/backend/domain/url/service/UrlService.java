package kdkd.youre.backend.domain.url.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlRequest;
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


    // 1. url 중복체크f
    @Transactional(readOnly = true)
    public boolean checkUrlDuplication(String url) {

        boolean urlDuplicate = urlRepository.existsByUrl(url);
        return urlDuplicate;
    }

    // 2. url 저장
    public Url add(UrlRequest request) {

        Optional<Category> category = categoryRepository.findById(request.getCategoryId());

        if(!category.isEmpty()){
            Url url = Url.builder()
                    .url(request.getUrl())
                    .title(request.getTitle())
                    .category(category.get())
                    .build();
            return urlRepository.save(url);
        }

        // 에러로 던지기
        return null;
    }

}
