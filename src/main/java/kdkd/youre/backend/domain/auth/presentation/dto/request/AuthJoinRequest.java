package kdkd.youre.backend.domain.auth.presentation.dto.request;

import lombok.*;

@Getter
@Builder
public class AuthJoinRequest {

    private String loginId;
    private String password;
    private String nickname;
    private String email;
}
