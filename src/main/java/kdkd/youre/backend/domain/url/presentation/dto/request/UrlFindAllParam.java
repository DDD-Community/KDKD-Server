package kdkd.youre.backend.domain.url.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UrlFindAllParam {

    private Long categoryId;
    private Long tagId;
    private String urlKeyword;
    private Boolean isWatched;
    private String order;
}
