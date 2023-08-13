package kdkd.youre.backend.domain.url.presentation.dto.response;

import kdkd.youre.backend.domain.category.presentation.dto.response.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UrlFindResponse {
    private String urlAddress;
    private String name;
    private String thumbnail;
    private CategoryDto category;
    private List<String> tag;
    private String memo;
    private Boolean isWatchedLater;
}



