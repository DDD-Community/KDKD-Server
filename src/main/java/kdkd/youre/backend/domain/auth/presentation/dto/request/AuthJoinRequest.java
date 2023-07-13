package kdkd.youre.backend.domain.auth.presentation.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthJoinRequest {

    private String loginId;
    private String password;
    private String nickname;
    private String email;
}
