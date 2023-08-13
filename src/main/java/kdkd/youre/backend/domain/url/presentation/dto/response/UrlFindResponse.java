package kdkd.youre.backend.domain.url.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UrlFindResponse {
    private String url;
    private String name;
    private String thumbnail;
    private String category;
    private String tag;
    private String memo;
    private String isWatchedLater;

}
