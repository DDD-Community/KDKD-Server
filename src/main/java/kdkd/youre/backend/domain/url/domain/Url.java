package kdkd.youre.backend.domain.url.domain;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.common.domain.BaseTimeEntity;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.url.presentation.dto.request.UrlUpdateRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    private String urlAddress;      // url 주소
    private String name;            //url 이름
    private String thumbnail;       // 썸네일 이미지
    private String memo;            // 메모
    private Boolean isWatchedLater; // 나중에 보기 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // Url은 Category 제거 시 함께 제거
    private Category category;

    @Builder
    public Url(
            String urlAddress,
            String name,
            String thumbnail,
            String memo,
            Boolean isWatchedLater,
            Category category) {
        this.urlAddress = urlAddress;
        this.name = name;
        this.thumbnail = thumbnail;
        this.memo = memo;
        this.isWatchedLater = isWatchedLater;
        this.category = category;
    }

    public Boolean isPublishedBy(Member member) {
        return this.getCategory().isPublishedBy(member);
    }

    public void updateUrl(UrlUpdateRequest request, Category category) {

        this.name = request.getName();
        this.category = category;
        this.memo = request.getMemo();
        this.isWatchedLater = request.getIsWatcedLater();
    }
}
