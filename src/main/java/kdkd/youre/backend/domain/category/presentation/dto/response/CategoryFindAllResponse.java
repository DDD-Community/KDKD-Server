package kdkd.youre.backend.domain.category.presentation.dto.response;

import kdkd.youre.backend.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryFindAllResponse {

    // https://www.npmjs.com/package/@minoru/react-dnd-treeview 형식 맞춤
    private Long id;            // 카테고리 id
    private Long parent;        // 부모 카테고리 id
    private Boolean droppable;  // 하위에 카테고리 생성 가능한지 여부 (depth 3이면 false, 1 또는 2면 true)
    private String text;        // 카테고리 이름

    public static CategoryFindAllResponse from(Category category) {

        return CategoryFindAllResponse.builder()
                .id(category.getId())
                .parent(category.getParentId())
                .droppable(category.getDroppable())
                .text(category.getName())
                .build();
    }
}
