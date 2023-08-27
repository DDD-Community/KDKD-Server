package kdkd.youre.backend.domain.url.presentation.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class UrlSaveRequest {
    private String urlAddress;
    private String name;
    private String thumbnail;
    private Long categoryId;
    private List<String> tag;
    private String memo;
    private Boolean isWatcedLater;
}