package kdkd.youre.backend.domain.url.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UrlFindAllParam {
    private Long categoryId;
    private String urlKeyword;
    private Boolean isWatched;
    private String order;
}
