package kdkd.youre.backend.domain.auth.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleAuthDto {

    private String sub;
    private String name;
    private String email;
    private String picture;
}
