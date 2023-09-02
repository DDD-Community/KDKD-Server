package kdkd.youre.backend.domain.category.presentation.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
public class CategorySaveRequest {
    private String name;
    private Long parentId;
}
