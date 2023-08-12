package kdkd.youre.backend.domain.url.domain;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    private String url; // url 주소
    private String title; //url 타이틀
    private String thumbnail; // 썸네일 이미지
    private String memo; // 메모
    private String tag; // 태그

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // category id

    @Builder
    public Url(
            String url,
            String title,
            String thumbnail,
            String memo,
            String tag,
            Category category) {
        this.url = url;
        this.title = title;
        this.thumbnail = thumbnail;
        this.memo = memo;
        this.tag = tag;
        this.category = category;
    }

    public Boolean isPublishedBy(Member member) {
        return this.getCategory().isPublishedBy(member);
    }
}
