package kdkd.youre.backend.domain.url.presentation.dto.response;

import kdkd.youre.backend.domain.url.domain.Url;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UrlDto {

    private String urlAddress;
    private String name;
    private String thumbnail;
    private Long categoryId;
    //    private CategoryDto category;
    private List<String> tag;
    private String memo;

    public static UrlDto from(Url url, List<String> tagNames) {
        return UrlDto.builder()
                .urlAddress(url.getUrlAddress())
                .name(url.getName())
                .categoryId(url.getCategory().getId())
//                .category(CategoryDto.from(url))
                .tag(tagNames)
                .thumbnail(url.getThumbnail())
                .memo(url.getMemo())
                .build();
    }
}
