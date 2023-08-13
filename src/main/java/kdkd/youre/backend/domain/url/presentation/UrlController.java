package kdkd.youre.backend.domain.url.presentation;

import kdkd.youre.backend.domain.url.presentation.dto.request.UrlRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlAddressCheckResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlCheckResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlSaveResponse;
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

    @PostMapping("")
    public ResponseEntity<UrlSaveResponse> saveUrl(@RequestBody UrlRequest request) {

        UrlSaveResponse response = urlService.saveUrl(request);
        return ResponseEntity.ok().body(response);
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

        // 조회 APi
        UrlFindResponse response = urlService.findUrl(urlId, principalDetails.getMember());

        return ResponseEntity.ok().body(response);
    }
}
