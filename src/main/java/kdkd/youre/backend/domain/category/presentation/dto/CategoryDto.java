package kdkd.youre.backend.domain.category.presentation.dto;

import kdkd.youre.backend.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {

    private Long categoryId;
    private String fullName;

    public static CategoryDto from(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getId())
                .fullName(category.getFullName())
                .build();
    }
}
