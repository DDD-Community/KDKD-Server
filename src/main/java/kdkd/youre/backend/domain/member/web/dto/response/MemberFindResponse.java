package kdkd.youre.backend.domain.member.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFindResponse {

    private Long id;
    private String email;
}
