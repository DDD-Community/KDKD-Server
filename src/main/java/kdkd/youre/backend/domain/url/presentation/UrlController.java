package kdkd.youre.backend.domain.url.presentation;

import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;

import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindAllResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
import kdkd.youre.backend.domain.url.service.UrlService;
import kdkd.youre.backend.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("")
    public ResponseEntity<IdResponse> saveUrl(@RequestBody UrlSaveRequest request,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        IdResponse response = urlService.saveUrl(request, principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }

    //Url 삭제
    @DeleteMapping("{urlId}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long urlId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {

        urlService.deleteUrl(urlId, principalDetails.getMember());
        return ResponseEntity.noContent().build();
    }

    //Url 상세조회
    @GetMapping("")
    public ResponseEntity<UrlFindResponse> findUrl(@RequestParam String address,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UrlFindResponse response = urlService.findUrl(address, principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }

    //Url 전체조회
    @GetMapping("/find")
    public ResponseEntity<UrlFindAllResponse> findAllUrl(@ModelAttribute UrlFindAllParam params,
                                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UrlFindAllResponse response = urlService.findAllUrl(params, principalDetails.getMember());

        return ResponseEntity.ok().body(response);
    }
}
