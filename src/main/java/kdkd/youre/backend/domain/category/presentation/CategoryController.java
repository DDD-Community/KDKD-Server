package kdkd.youre.backend.domain.category.presentation;

import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryUpdateRequest;
import kdkd.youre.backend.domain.category.service.CategoryService;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    //Category 저장
    @PostMapping("")
    public ResponseEntity<IdResponse> saveCategory(@RequestBody CategorySaveRequest request,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        IdResponse response = categoryService.saveCategory(request, principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }

    //Category 수정
    @PatchMapping("{categoryId}/name")
    public ResponseEntity<Void> updateCategory(@PathVariable Long categoryId,
                                               @RequestBody CategoryUpdateRequest request,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        categoryService.updateCategory(categoryId, request, principalDetails.getMember());
        return ResponseEntity.ok().build();
    }
}
