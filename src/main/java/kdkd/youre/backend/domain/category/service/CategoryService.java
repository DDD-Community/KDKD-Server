package kdkd.youre.backend.domain.category.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.url.service.UrlService;
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

    private final UrlService urlService;

    public IdResponse saveCategory(CategorySaveRequest request, Member member) {

        //카테고리 중복체크
        Category category = categoryRepository.findByName(request.getName());

        validateCategoryOwnerShip(category, member);

//        if (category != null) {
//            throw new CustomException(ErrorCode.CONFLICT_CATEGORY);
//        }

        Category category1 = Category.builder()
                .name(request.getName())
                .member(member)
                .parent(category)
                .build();

        categoryRepository.save(category1);

        return IdResponse.builder()
                .id(category1.getId())
                .build();
    }
    public void validateCategoryOwnerShip(Category category, Member member) { // TODO: 위치 혹은 이름 더 적절하게 변경하기
        if (!category.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }
}
