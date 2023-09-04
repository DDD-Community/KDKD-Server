package kdkd.youre.backend.domain.category.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryBookmarkUpdateRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryPositionUpdateRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryNameUpdateRequest;
import kdkd.youre.backend.domain.category.presentation.dto.response.CategoryBookmarkFindAllResponse;
import kdkd.youre.backend.domain.category.presentation.dto.response.CategoryFindAllResponse;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public IdResponse saveCategory(CategorySaveRequest request, Member member) {

        checkDuplicateByName(request.getName(), member);

        Category parentCategory = Optional.ofNullable(request.getParentId())
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY)))
                .orElse(null);

        String fullName = Optional.ofNullable(parentCategory)
                .map(parent -> parent.getChildFullName(request.getName()))
                .orElse(request.getName());

        //TODO: 추후 도메인 로직으로 분리 예정
        Long position;
        if (parentCategory != null) {
            position = categoryRepository.findMaxPositionForMemberAndParent(member, parentCategory);
        } else {
            position = categoryRepository.findMaxPositionByMember(member);
        }

        Long newPosition = Optional.ofNullable(position)
                .map(p -> (p / 10000L + 1) * 10000L)
                .orElse(10000L);

        Long depth = Optional.ofNullable(parentCategory)
                .map(Category::getDepth)
                .map(d -> d + 1)
                .orElse(1L);

        Category category = Category.builder()
                .name(request.getName())
                .fullName(fullName)
                .parent(parentCategory)
                .member(member)
                .depth(depth)
                .isBookmarked(false)
                .position(newPosition)
                .build();

        categoryRepository.save(category);

        return IdResponse.builder()
                .id(category.getId())
                .build();
    }

    public void updateCategoryName(Long categoryId, CategoryNameUpdateRequest request, Member member) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

        validateCategoryOwnerShip(category, member);
        checkDuplicateByName(request.getName(), member);

        category.updateCategoryName(request);
    }

    public void updateCategoryBookmark(Long categoryId, CategoryBookmarkUpdateRequest request, Member member) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

        validateCategoryOwnerShip(category, member);
        category.updateCategoryBookmark(request);
    }

    public void updateCategoryPosition(Long categoryId, CategoryPositionUpdateRequest request, Member member) {

        // TODO: null null 일때 로직 추가 해야함
        Long currentPosition;

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

        Category parentCategory = Optional.ofNullable(request.getParentId())
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY)))
                .orElse(null);

        String fullName = Optional.ofNullable(parentCategory)
                .map(parent -> parent.getChildFullName(category.getName()))
                .orElse(category.getName());

        // 깊이 계산
        Long depth = categoryRepository.findDepth(member, request);

        Long nextPosition;
        Long newPosition = null;

        if (request.getParentId() == null) {

            currentPosition = categoryRepository.findCurrentPosition(member, request);
            List<Long> nextPositions = categoryRepository.findNextPosition(member, request, currentPosition);

            if (nextPositions.isEmpty()) {
                newPosition = ((currentPosition / 10000L) + 1) * 10000L;
            } else {
                nextPosition = nextPositions.get(0);
                newPosition = (currentPosition + nextPosition) / 2;
            }

            category.updateCategoryPosition(newPosition, depth, null, fullName);

        } else {
            Category parentCategory2 = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

            if (request.getAboveTargetId() == null) {
                category.updateCategoryPosition(10000L, depth + 1, parentCategory2, fullName);
            } else {
                currentPosition = categoryRepository.findCurrentPosition(member, request);
                List<Long> nextPositions = categoryRepository.findNextPosition(member, request, currentPosition);

                if (nextPositions.isEmpty()) {
                    newPosition = ((currentPosition / 10000L) + 1) * 10000L;
                } else {
                    nextPosition = nextPositions.get(0);
                    newPosition = (currentPosition + nextPosition) / 2;
                }
                category.updateCategoryPosition(newPosition, depth, parentCategory2, fullName);
            }
        }
    }

    public void deleteCategory(Long categoryId, Member member) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

        validateCategoryOwnerShip(category, member);
        categoryRepository.delete(category);
    }

    public void validateCategoryOwnerShip(Category category, Member member) { // TODO: 위치 혹은 이름 더 적절하게 변경하기

        if (!category.isPublishedBy(member))
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
    }

    private void checkDuplicateByName(String name, Member member) {

        if (categoryRepository.existsByNameAndMember(name, member)) {
            throw new CustomException(ErrorCode.CONFLICT_CATEGORY);
        }
    }

    public List<CategoryFindAllResponse> findAllCategory(Member member) {

        List<Category> categories = categoryRepository.findAllByMember(member);
        return categories.stream()
                .map(CategoryFindAllResponse::from)
                .collect(Collectors.toList());
    }

    public List<CategoryBookmarkFindAllResponse> findAllCategoryBookmark(Member member) {

        List<Category> categories = categoryRepository.findAllByMemberAndIsBookmarkedTrue(member);
        return categories.stream()
                .map(CategoryBookmarkFindAllResponse::from)
                .collect(Collectors.toList());
    }
}
