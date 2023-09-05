package kdkd.youre.backend.domain.category.presentation.dto.response;

import kdkd.youre.backend.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryTreeResponse {

    // https://www.npmjs.com/package/@minoru/react-dnd-treeview 형식 맞춤
    private Long id;            // 카테고리 id
    private Long parent;        // 부모 카테고리 id
    private Boolean droppable;  // 하위에 카테고리 생성 가능한지 여부 (depth 3이면 false, 1 또는 2면 true)
    private String text;        // 카테고리 이름
    private DataDto data;       // 기타 정보(순서)

    public static CategoryTreeResponse from(Category category) {

        return CategoryTreeResponse.builder()
                .id(category.getId())
                .parent(category.getParentId())
                .droppable(category.getDroppable())
                .text(category.getName())
                .data(DataDto.from(category.getPosition()))
                .build();
    }

    @Getter
    @Builder
    private static class DataDto {

        private Long order;     // 카테고리 순서

        private static DataDto from(Long order) {

            return DataDto.builder()
                    .order(order)
                    .build();
        }
    }
}
