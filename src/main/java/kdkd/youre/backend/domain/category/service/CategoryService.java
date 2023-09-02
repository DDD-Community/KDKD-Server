package kdkd.youre.backend.domain.category.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
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
        checkDuplicateByName(request.getName(), member);

        Category parentCategory = Optional.ofNullable(request.getParentId())
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY)))
                .orElse(null);

        String fullName = Optional.ofNullable(parentCategory)
                .map(parent -> parent.getChildFullName(request.getName()))
                .orElse(request.getName());
        Long position = categoryRepository.findMaxPositionByMember(member);
        Long newPosition = (position == null) ? 10000L : ((position / 10000L) + 1) * 10000L;

        Category category = Category.builder()
                .name(request.getName())
                .fullName(fullName)
                .parent(parentCategory)
                .member(member)
                .depth(1L)
                .isBookmarked(false)
                .position(newPosition)
                .build();

        categoryRepository.save(category);

        return IdResponse.builder()
                .id(category.getId())
                .build();
    }

    private void checkDuplicateByName(String name, Member member) {

        if (categoryRepository.existsByNameAndMember(name, member)) {
            throw new CustomException(ErrorCode.CONFLICT_CATEGORY);
        }
    }
}
