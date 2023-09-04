package kdkd.youre.backend.domain.auth.presentation.dto.response;

import lombok.*;

@Getter
@Builder
public class AuthLoginResponse {

    private String accessToken;
    private String refreshToken;
    private String role;
}
