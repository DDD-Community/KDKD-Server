package kdkd.youre.backend.domain.category.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryUpdateRequest;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.member.domain.Member;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public IdResponse saveCategory(CategorySaveRequest request, Member member) {

        //카테고리 중복체크
        Boolean isDuplicated = categoryRepository.existsByNameAndMember(request.getName(), member);

        Category parentCategory = Optional.ofNullable(request.getParentId())
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY)))
                .orElse(null);

        if (isDuplicated) {
            throw new CustomException(ErrorCode.CONFLICT_CATEGORY);
        }

        Category category = Category.builder()
                .name(request.getName())
                .parent(parentCategory)
                .member(member)
                .build();

        categoryRepository.save(category);

        return IdResponse.builder()
                .id(category.getId())
                .build();
    }

    public void updateCategory(Long categoryId, CategoryUpdateRequest request, Member member) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

        validateCategoryOwnerShip(category, member);

        Boolean isDuplicated = categoryRepository.existsByNameAndMember(request.getName(), member);

        if (isDuplicated) {
            throw new CustomException(ErrorCode.CONFLICT_CATEGORY);
        }
        category.updateCategory(request);
    }

    public void validateCategoryOwnerShip(Category category, Member member) { // TODO: 위치 혹은 이름 더 적절하게 변경하기
        if (!category.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }
}
