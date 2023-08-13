package kdkd.youre.backend.domain.url.domain;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    private String urlAddress; // url 주소
    private String name; //url 타이틀
    private String thumbnail; // 썸네일 이미지
    private String memo; // 메모
    private Boolean isWatchedLater; // 나중에 보기 여부
    private Date createdAt; // 등록일자
    private Date updateAt; //수정일자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // category id


    @Builder
    public Url(
            String urlAddress,
            String name,
            String thumbnail,
            String memo,
            Boolean isWatchedLater,
            Date createdAt,
            Date updateAt,
            Category category) {
        this.urlAddress = urlAddress;
        this.name = name;
        this.thumbnail = thumbnail;
        this.memo = memo;
        this.isWatchedLater = isWatchedLater;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.category = category;
    }

    public Boolean isPublishedBy(Member member) {

        return this.getCategory().isPublishedBy(member);
    }
}
