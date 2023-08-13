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

    public Category saveCategory(CategorySaveRequest request) {

//        Category category = categoryRepository.findByCategoryFullName(request.getName());


//        Category Category.builder()
//                .name(request.getName())
//                .build();
//
//        IdResponse response = IdResponse.builder()
//                .id(Category.getId())
//                .build();

//        categoryRepository.save(Category);
        return null;
    }
}
