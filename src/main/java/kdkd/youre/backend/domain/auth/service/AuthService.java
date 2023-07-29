package kdkd.youre.backend.domain.auth.service;

import kdkd.youre.backend.domain.auth.presentation.dto.request.AuthJoinRequest;
import kdkd.youre.backend.domain.auth.presentation.dto.request.AuthLoginRequest;
import kdkd.youre.backend.domain.auth.presentation.dto.request.AuthLoginResponse;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.member.domain.repository.MemberRepository;
import kdkd.youre.backend.domain.refresh_token.service.RefreshTokenService;
import kdkd.youre.backend.global.dto.response.IdResponse;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import kdkd.youre.backend.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    // 일반 회원가입 (테스트용)
    public IdResponse join(AuthJoinRequest request) {

        Member member = Member.builder()
                .loginId(request.getLoginId())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nickname(request.getNickname())
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);

        IdResponse response = IdResponse.builder()
                .id(member.getId())
                .build();

        return response;
    }

    // 일반 로그인 (테스트용)
    public AuthLoginResponse login(AuthLoginRequest request) {

        // 아이디와 비밀번호 유효성 체크
        Member findMember = memberRepository.findByLoginId(request.getLoginId());
        if(findMember == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ID);
        }
        if(!passwordEncoder.matches(request.getPassword(), findMember.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        // 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(findMember.getLoginId(), findMember.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(findMember.getLoginId());
        refreshTokenService.saveRefreshToken(refreshToken, findMember.getLoginId());

        AuthLoginResponse response = AuthLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(findMember.getRole())
                .build();

        return response;
    }
}
