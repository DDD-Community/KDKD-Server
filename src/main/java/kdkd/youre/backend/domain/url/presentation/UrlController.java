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

    // Url 저장
    @PostMapping("")
    public ResponseEntity<IdResponse> saveUrl(@RequestBody UrlSaveRequest request,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        IdResponse response = urlService.saveUrl(request, principalDetails.getMember()); // TODO: Member가 연관관계 필드를 갖게 되면 member 새로 찾아오는 로직 추가 필요함
        return ResponseEntity.ok().body(response);
    }

    // Url 수정
    @PatchMapping("/{urlId}")
    public ResponseEntity<Void> updateUrl(@PathVariable Long urlId,
                                          @RequestBody UrlUpdateRequest request,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {

        urlService.updateUrl(urlId, request, principalDetails.getMember());
        return ResponseEntity.ok().build();
    }

    // Url 삭제
    @DeleteMapping("/{urlId}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long urlId,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {

        urlService.deleteUrl(urlId, principalDetails.getMember());
        return ResponseEntity.noContent().build();
    }

    // TODO: 중복 검사 로직 분리하기
    // Url 상세조회
    @GetMapping("")
    public ResponseEntity<UrlFindResponse> findUrl(@RequestParam String address,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UrlFindResponse response = urlService.findUrl(address, principalDetails.getMember());
        return ResponseEntity.ok().body(response);
    }

    // TODO: 상세 조회랑 목록 조회 엔드포인트 정리하고, 목록 조회는 검색 api로 기능 분리하기
    // Url 전체 목록 조회
    @GetMapping("/find")
    public ResponseEntity<UrlFindAllResponse> findAllUrl(@ModelAttribute UrlFindAllParam params,
                                                         @RequestParam(defaultValue = "1") int pageNo,
                                                         @RequestParam(defaultValue = "25") int pageSize,
                                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        UrlFindAllResponse response = urlService.findAllUrl(principalDetails.getMember(), params, pageable);

        return ResponseEntity.ok().body(response);
    }
}
