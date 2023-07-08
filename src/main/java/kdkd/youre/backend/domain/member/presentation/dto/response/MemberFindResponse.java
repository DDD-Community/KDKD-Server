package kdkd.youre.backend.domain.member.presentation.dto.response;

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
