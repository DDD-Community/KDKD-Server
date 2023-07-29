package kdkd.youre.backend.domain.refresh_token.service;

import kdkd.youre.backend.domain.refresh_token.domain.RefreshToken;
import kdkd.youre.backend.domain.refresh_token.domain.repository.RefreshTokenRepository;
import kdkd.youre.backend.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String token, String loginId) {

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .keyLoginId(loginId)
                .build();

        if(refreshTokenRepository.existsByKeyLoginId(loginId)){
            refreshTokenRepository.deleteByKeyLoginId(loginId);
        }

        refreshTokenRepository.save(refreshToken);
    }
}
