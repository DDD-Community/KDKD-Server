package kdkd.youre.backend.domain.tag.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagFindAllResponse {
    private String name;

    public static TagFindAllResponse from(String name) {

        return TagFindAllResponse.builder()
                .name(name)
                .build();
    }
}
