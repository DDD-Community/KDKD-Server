package kdkd.youre.backend.domain.url.presentation;

import kdkd.youre.backend.domain.url.presentation.dto.request.UrlUpdateRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlAddressCheckResponse;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;

import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
import kdkd.youre.backend.domain.url.service.UrlService;
import kdkd.youre.backend.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("urls")
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/check-address")
    public ResponseEntity<UrlAddressCheckResponse> checkUrlAddress(@RequestParam String address) {

        UrlAddressCheckResponse response = urlService.checkUrlAddress(address);
        return ResponseEntity.ok().body(response);
    }

    // url 저장
    @PostMapping("")
    public ResponseEntity<IdResponse> saveUrl(@RequestBody UrlSaveRequest request,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        IdResponse response = urlService.saveUrl(request, principalDetails.getMember()); // TODO: Membmer가 연관관계 필드를 갖게 되면 member 새로 찾아오는 로직 추가 필요함
        return ResponseEntity.ok().body(response);
    }

    // url 수정
    @PatchMapping("{urlId}")
    public ResponseEntity<Void> updateUrl(@PathVariable Long urlId,
                                          @RequestBody UrlUpdateRequest request,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {

        urlService.updateUrl(urlId, request, principalDetails.getMember());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{urlId}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long urlId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {

        urlService.deleteUrl(urlId, principalDetails.getMember());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{urlId}")
    public ResponseEntity<UrlFindResponse> findUrl(@PathVariable Long urlId,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UrlFindResponse response = urlService.findUrl(urlId, principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }
}
