package kdkd.youre.backend.domain.category.presentation.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategorySaveRequest {
    private String name;
    private Long parentId;
}
