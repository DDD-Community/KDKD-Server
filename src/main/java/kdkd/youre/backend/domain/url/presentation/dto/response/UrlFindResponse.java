package kdkd.youre.backend.domain.url.presentation.dto.response;

import kdkd.youre.backend.domain.url.domain.Url;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlFindResponse {
    //저장 post
    private Boolean urlCheck;
}
