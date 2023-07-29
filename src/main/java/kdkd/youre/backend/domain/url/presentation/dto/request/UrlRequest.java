package kdkd.youre.backend.domain.url.presentation.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
public class UrlRequest {

    private String url;
    private String title;
    private Long categoryId;
    private String categoryName;
}
