package kdkd.youre.backend.domain.category.presentation.dto.response;

import kdkd.youre.backend.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {
    private Long id;
    private String name;

    public static CategoryDto from(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
