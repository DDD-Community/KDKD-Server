package kdkd.youre.backend.domain.tag.domain;

import kdkd.youre.backend.domain.common.domain.BaseTimeEntity;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.url.domain.Url;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id")
    private Url url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Tag(String name, Url url, Member member) {
        this.name = name;
        this.url = url;
        this.member = member;
    }
}
