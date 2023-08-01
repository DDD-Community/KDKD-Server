package kdkd.youre.backend.domain.url.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlDeleteRequest {
    private String url;
    private String title;
    private Long categoryId;
    private String categoryName;
    private Long urlId;
}