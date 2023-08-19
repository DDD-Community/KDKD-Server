package kdkd.youre.backend.domain.category.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public IdResponse saveCategory(CategorySaveRequest request) {

        //카테고리 중복체크
        Category category = categoryRepository.findByName(request.getName());

        if (category != null) {
            throw new CustomException(ErrorCode.CONFLICT_CATEGORY);
        }

        Category category1 = Category.builder()
                .name(request.getName())
                .build();

        categoryRepository.save(category1);

        return IdResponse.builder()
                .id(category1.getId())
                .build();
    }
}
