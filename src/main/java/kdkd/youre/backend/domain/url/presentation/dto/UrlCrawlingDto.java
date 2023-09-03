package kdkd.youre.backend.domain.url.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UrlCrawlingDto {

    private String address;
    private String name;
    private String thumbnail;

    public static UrlCrawlingDto from(String address, String name, String thumbnail) {
        return UrlCrawlingDto.builder()
                .address(address)
                .name(name)
                .thumbnail(thumbnail)
                .build();
    }
}
