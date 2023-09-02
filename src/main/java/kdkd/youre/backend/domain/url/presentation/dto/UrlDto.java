package kdkd.youre.backend.domain.url.presentation.dto;

import kdkd.youre.backend.domain.url.domain.Url;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UrlDto {

    private Long urlId;
    private String urlAddress;
    private String name;
    private String thumbnail;
    private Long categoryId;
    private List<String> tag;
    private String memo;

    public static UrlDto from(Url url, List<String> tagNames) {
        return UrlDto.builder()
                .urlId(url.getId())
                .urlAddress(url.getUrlAddress())
                .name(url.getName())
                .thumbnail(url.getThumbnail())
                .categoryId(url.getCategory().getId())
                .tag(tagNames)
                .memo(url.getMemo())
                .build();
    }
}
