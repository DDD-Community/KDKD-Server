package kdkd.youre.backend.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.secret_refresh}")
    private String refreshSecretKey;

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 토큰 유효시간 30분
    private long accessTokenValidTime = Duration.ofMinutes(30).toMillis();
    // 리프레시 토큰 유효시간 2주
    private long refreshTokenValidTime = Duration.ofDays(14).toMillis();

    private String buildToken(Claims claims, Date issuedAt, Date TokenExpiresIn, String key) {

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(issuedAt) // 토큰 발행 시간
                .setExpiration(TokenExpiresIn) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // accessToken 생성
    private String generateAccessToken(String loginId, String role) {

        Claims claims = Jwts.claims().setSubject(loginId); // JWT payload에 저장되는 정보 단위
        claims.put("role", role);
        Date issuedAt = new Date();
        Date TokenExpiresIn = new Date(issuedAt.getTime() + accessTokenValidTime);

        return buildToken(claims, issuedAt, TokenExpiresIn, secretKey);
    }

    // refreshToken 생성
    private String generateRefreshToken(String loginId) {

        Claims claims = Jwts.claims().setSubject(loginId);
        Date issuedAt = new Date();
        Date TokenExpiresIn = new Date(issuedAt.getTime() + refreshTokenValidTime);

        return buildToken(claims, issuedAt, TokenExpiresIn, refreshSecretKey);
    }

    // accessToken에서 userPk(loginId) 추출
    public String getLoginId(String token) {

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
