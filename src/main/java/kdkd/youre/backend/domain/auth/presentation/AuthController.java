package kdkd.youre.backend.domain.auth.presentation;

import kdkd.youre.backend.domain.auth.presentation.dto.request.AuthJoinRequest;
import kdkd.youre.backend.domain.auth.service.AuthService;
import kdkd.youre.backend.global.dto.response.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    // 일반 회원가입 (테스트용)
    @PostMapping("join")
    public ResponseEntity<IdResponse> join(@RequestBody AuthJoinRequest request) {

        IdResponse response = authService.join(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
