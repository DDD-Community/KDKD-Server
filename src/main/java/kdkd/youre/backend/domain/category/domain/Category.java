package kdkd.youre.backend.domain.category.domain;

import kdkd.youre.backend.domain.category.presentation.dto.request.CategoryUpdateRequest;
import kdkd.youre.backend.domain.common.domain.BaseTimeEntity;
import kdkd.youre.backend.domain.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;            // 카테고리 이름
    private String fullName;        // 카테고리 경로 포함 이름
    private Long position;          // 카테고리 순서
    private Long depth;             // 카테고리 깊이
    private Boolean isBookmarked;   // 즐겨찾기 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // 자식 Category는 부모 Category 제거 시 함께 제거
    private Category parent;        // 상위 카테고리 고유 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // Category는 Member 제거 시 함께 제거
    private Member member;          // 회원 고유 번호

    @Builder
    public Category(String name,
                    String fullName,
                    Long position,
                    Long depth,
                    Boolean isBookmarked,
                    Category parent,
                    Member member) {
        this.name = name;
        this.fullName = fullName;
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

    public String getChildFullName(String childName) {

        return this.fullName + "/" + childName;
    }
}
