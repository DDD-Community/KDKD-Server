package kdkd.youre.backend.domain.url.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UrlUpdateRequest {

    private String name;
    private Long categoryId;
    private List<String> tag;
    private String memo;
    private Boolean isWatcedLater;
}
