package kdkd.youre.backend.global.security;

import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.member.domain.repository.MemberRepository;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import kdkd.youre.backend.global.security.auth.PrincipalDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            MemberRepository memberRepository,
            JwtTokenProvider jwtTokenProvider) {

        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(JwtProperties.HEADER_STRING);
        System.out.println("header = " + header);

        if (header == null || header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = request
                .getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        // 토큰 검증(인증)
        String username = jwtTokenProvider.getUsernameFromToken(token);
        if (username != null) {
            Member member = memberRepository.findByLoginId(username);

            // 권한 처리(Authentication 객체 생성해 세션에 저장)
            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails,
                    null,
                    principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
