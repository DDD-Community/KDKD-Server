package kdkd.youre.backend.domain.category.domain;

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

    private String title; // 디렉토리 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원 고유 번호

    @Builder
    public Category(String title, Member member) {
        this.title = title;
        this.member = member;
    }

    public Boolean isPublishedBy(Member member) {
        return this.member.equals(member);
    }
}
