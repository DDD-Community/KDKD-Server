package kdkd.youre.backend.domain.auth.presentation.dto.request;

import lombok.*;

@Getter
@Builder
public class AuthLoginResponse {

    private String accessToken;
    private String refreshToken;
    private String role;
}
