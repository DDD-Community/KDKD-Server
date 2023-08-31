package kdkd.youre.backend.domain.url.presentation;

import kdkd.youre.backend.domain.url.presentation.dto.request.UrlUpdateRequest;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlFindAllParam;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlSaveRequest;

import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindAllResponse;
import kdkd.youre.backend.domain.url.presentation.dto.response.UrlFindResponse;
import kdkd.youre.backend.domain.url.service.UrlService;
import kdkd.youre.backend.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("urls")
public class UrlController {

    private final UrlService urlService;

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
                                                         @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                         @RequestParam(defaultValue = "1") int pageNo,
                                                         @RequestParam(defaultValue = "25") int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        UrlFindAllResponse response = urlService.findAllUrl(params, principalDetails.getMember(), pageable);

        return ResponseEntity.ok().body(response);
    }
}
