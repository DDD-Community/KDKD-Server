package kdkd.youre.backend.domain.category.presentation;

import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryBookmarkUpdateRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryPositionUpdateRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryNameUpdateRequest;
import kdkd.youre.backend.domain.category.presentation.dto.response.CategoryBookmarkFindAllResponse;
import kdkd.youre.backend.domain.category.presentation.dto.response.CategoryTreeResponse;
import kdkd.youre.backend.domain.category.service.CategoryService;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    //Category 저장
    @PostMapping("")
    public ResponseEntity<CategoryTreeResponse> saveCategory(@RequestBody CategorySaveRequest request,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        CategoryTreeResponse response = categoryService.saveCategory(request, principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }

    //Category Name 수정
    @PatchMapping("{categoryId}/name")
    public ResponseEntity<Void> updateCategoryName(@PathVariable Long categoryId,
                                                   @RequestBody CategoryNameUpdateRequest request,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        categoryService.updateCategoryName(categoryId, request, principalDetails.getMember());
        return ResponseEntity.ok().build();
    }

    //Category Bookmark 수정
    @PatchMapping("{categoryId}/bookmark")
    public ResponseEntity<Void> updateCategoryBookmark(@PathVariable Long categoryId,
                                                       @RequestBody CategoryBookmarkUpdateRequest request,
                                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {

        categoryService.updateCategoryBookmark(categoryId, request, principalDetails.getMember());
        return ResponseEntity.ok().build();
    }

    //Category Position 수정
    @PatchMapping("{categoryId}/position")
    public ResponseEntity<CategoryTreeResponse> updateCategoryPosition(@PathVariable Long categoryId,
                                                                                 @RequestBody CategoryPositionUpdateRequest request,
                                                                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {

        CategoryTreeResponse response = categoryService.updateCategoryPosition(categoryId, request, principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }

    // Category 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        categoryService.deleteCategory(categoryId, principalDetails.getMember());
        return ResponseEntity.noContent().build();
    }

    // Category 전체 목록 조회
    @GetMapping("")
    public ResponseEntity<List<CategoryTreeResponse>> findAllCategory(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<CategoryTreeResponse> response = categoryService.findAllCategory(principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }

    // Bookmark한 Category 전체 목록 조회
    @GetMapping("/bookmark")
    public ResponseEntity<List<CategoryBookmarkFindAllResponse>> findAllCategoryBookmark(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<CategoryBookmarkFindAllResponse> response = categoryService.findAllCategoryBookmark(principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }
}
