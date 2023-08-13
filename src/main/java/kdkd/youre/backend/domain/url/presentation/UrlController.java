package kdkd.youre.backend.domain.url.presentation;

import kdkd.youre.backend.domain.url.presentation.dto.response.UrlAddressCheckResponse;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;

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
    public ResponseEntity<IdResponse> saveUrl(@RequestBody UrlSaveRequest request) {

        IdResponse response = urlService.saveUrl(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{urlId}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long urlId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {

        urlService.deleteUrl(urlId, principalDetails.getMember());
        return ResponseEntity.noContent().build();
    }
}
