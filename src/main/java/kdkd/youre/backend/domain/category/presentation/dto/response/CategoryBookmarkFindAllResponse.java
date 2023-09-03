package kdkd.youre.backend.domain.category.presentation.dto.response;

import kdkd.youre.backend.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryBookmarkFindAllResponse {

    public Long id;
    public String name;

    public static CategoryBookmarkFindAllResponse from(Category category) {
        return CategoryBookmarkFindAllResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
