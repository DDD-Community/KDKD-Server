package kdkd.youre.backend.domain.auth.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleAuthDto {

    private String iss; // TODO: 없어도 되면 제거하기
    private String sub;
    private String name;
    private String email;
    private String picture;
}
