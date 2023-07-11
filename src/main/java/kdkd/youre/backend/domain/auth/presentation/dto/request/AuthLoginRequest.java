package kdkd.youre.backend.domain.auth.presentation.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginRequest {

    private String loginId;
    private String password;
}
