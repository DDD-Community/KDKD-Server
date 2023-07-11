package kdkd.youre.backend.domain.member.presentation;

import kdkd.youre.backend.domain.member.service.MemberService;
import kdkd.youre.backend.domain.member.presentation.dto.response.MemberFindResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 회원 정보 조회
    @GetMapping("/members")
    public ResponseEntity<MemberFindResponse> findMember() {

        MemberFindResponse response = memberService.findMember(1L);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
