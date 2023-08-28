package kdkd.youre.backend.domain.category.domain;

import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryUpdateRequest;
import kdkd.youre.backend.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name; // 디렉토리 이름
    private Long position; //디렉토리 순서
    private Long depth; //디렉토리 깊이
    private Boolean isBookmarked; //즐겨찾기 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; // 상위 카테고리 고유 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원 고유 번호

    @Builder
    public Category(String name,
                    Long position,
                    Long depth,
                    Boolean isBookmarked,
                    Category parent,
                    Member member) {
        this.name = name;
        this.position = position;
        this.depth = depth;
        this.isBookmarked = isBookmarked;
        this.parent = parent;
        this.member = member;
    }

    public Boolean isPublishedBy(Member member) {
        return this.member.equals(member);
    }

    public void updateCategory(CategoryUpdateRequest request) {
        this.name = request.getName();
    }
}
