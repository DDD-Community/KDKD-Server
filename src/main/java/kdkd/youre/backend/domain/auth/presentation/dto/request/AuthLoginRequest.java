package kdkd.youre.backend.domain.auth.presentation.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
public class AuthLoginRequest {

    private String loginId;
    private String password;
}
