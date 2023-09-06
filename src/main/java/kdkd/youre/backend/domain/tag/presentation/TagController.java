package kdkd.youre.backend.domain.tag.presentation;

import kdkd.youre.backend.domain.tag.presentation.dto.response.TagFindAllResponse;
import kdkd.youre.backend.domain.tag.service.TagService;
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
@RequestMapping("tags")
public class TagController {
    private final TagService tagService;

    // Tag 전체목록 조회
    @GetMapping("")
    public ResponseEntity<List<TagFindAllResponse>> findAllTag(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<TagFindAllResponse> response = tagService.findAllTag(principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }
}
