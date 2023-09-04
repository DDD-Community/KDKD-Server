package kdkd.youre.backend.domain.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthGoogleLoginRequest {

    private String idToken;
}
