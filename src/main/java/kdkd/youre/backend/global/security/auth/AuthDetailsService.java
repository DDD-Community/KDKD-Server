package kdkd.youre.backend.global.security.auth;

import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.member.domain.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginId(username);
        return new AuthDetails(member.getLoginId(), member.getRole());
    }
}
