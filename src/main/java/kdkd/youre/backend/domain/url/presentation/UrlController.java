package kdkd.youre.backend.domain.url.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import kdkd.youre.backend.domain.url.domain.Url;
import kdkd.youre.backend.domain.url.domain.repository.UrlRepository;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlRequest;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlSaveResponse;
import kdkd.youre.backend.domain.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UrlController {
    //서비스
    private final UrlService urlService;

    @GetMapping("/urls/url-check")
    public ResponseEntity<UrlFindResponse> checkUrlsDuplication(@RequestParam String url) {
        log.info("중복체크");
        return ResponseEntity.ok(new UrlFindResponse(urlService.checkUrlDuplication(url)));
    }

    @PostMapping("/urls")
    @JsonProperty("comment")
    public ResponseEntity<UrlSaveResponse> saveUrls(@RequestBody UrlRequest request) {

        log.info("URL저장");
        Url result = urlService.add(request);

        return ResponseEntity.ok(new UrlSaveResponse(result.getId()));
    }
}