package kdkd.youre.backend.domain.member.service;

import kdkd.youre.backend.domain.member.repository.Member;
import kdkd.youre.backend.domain.member.repository.MemberRepository;
import kdkd.youre.backend.domain.member.web.dto.response.MemberFindResponse;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public MemberFindResponse findMember(Long id) {

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        MemberFindResponse response = MemberFindResponse.builder()
                .id(findMember.getId())
                .email(findMember.getEmail())
                .build();

        return response;
    }
}
