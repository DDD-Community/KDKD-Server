package kdkd.youre.backend.domain.auth.presentation.dto.request;

import lombok.*;

@Getter
@Builder
public class AuthLoginRequest {

    private String loginId;
    private String password;
}
