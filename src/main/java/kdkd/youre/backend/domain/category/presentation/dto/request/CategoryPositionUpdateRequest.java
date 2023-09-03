package kdkd.youre.backend.domain.category.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryPositionUpdateRequest {
    private Long parentId;
    private Long aboveTargetId;
}
