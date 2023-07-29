package kdkd.youre.backend.domain.url.presentation;

import kdkd.youre.backend.domain.url.presentation.dto.request.UrlRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlCheckResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlSaveResponse;
import kdkd.youre.backend.domain.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("urls")
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/url-check")
    public ResponseEntity<UrlCheckResponse> checkUrlsDuplication(@RequestParam String url) {

        UrlCheckResponse response = urlService.checkUrl(url);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("")
    public ResponseEntity<UrlSaveResponse> saveUrl(@RequestBody UrlRequest request) {

        UrlSaveResponse response = urlService.saveUrl(request);
        return ResponseEntity.ok().body(response);
    }
}
