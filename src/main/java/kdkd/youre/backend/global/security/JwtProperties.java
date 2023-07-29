package kdkd.youre.backend.global.security;

import java.time.Duration;

public interface JwtProperties {

    Long ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(30).toMillis(); // 30분
    Long REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofDays(14).toMillis();  // 2주
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
