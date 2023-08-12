package kdkd.youre.backend.domain.tag.domain;

import kdkd.youre.backend.domain.url.domain.Url;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id")
    private Url url;

    // TODO: User 도메인이랑 ManyToOne 관계 형성

    @Builder
    public Tag(String name, Url url) {
        this.name = name;
        this.url = url;
    }
}
