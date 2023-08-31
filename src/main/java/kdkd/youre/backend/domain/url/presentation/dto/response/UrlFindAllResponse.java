package kdkd.youre.backend.domain.url.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UrlFindAllResponse {

    private int totalCount;
    private List<UrlDto> url;
}
