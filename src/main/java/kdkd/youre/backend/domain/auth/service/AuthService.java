package kdkd.youre.backend.domain.auth.service;

import kdkd.youre.backend.domain.auth.presentation.dto.request.AuthJoinRequest;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.member.domain.repository.MemberRepository;
import kdkd.youre.backend.global.dto.response.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
