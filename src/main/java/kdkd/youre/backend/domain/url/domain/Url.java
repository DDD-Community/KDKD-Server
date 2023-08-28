package kdkd.youre.backend.domain.url.domain;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    private String urlAddress; // url 주소
    private String name; //url 이름
    private String thumbnail; // 썸네일 이미지
    private String memo; // 메모
    private Boolean isWatchedLater; // 나중에 보기 여부

    // 일단추가
//    @CreatedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
//    private Timestamp createdAt; // 등록일자
//
//    @LastModifiedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
//    private Timestamp updatedAt; //수정일자

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;


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
            Timestamp createdAt,
            Timestamp updatedAt,
            Category category) {
        this.urlAddress = urlAddress;
        this.name = name;
        this.thumbnail = thumbnail;
        this.memo = memo;
        this.isWatchedLater = isWatchedLater;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Boolean isPublishedBy(Member member) {

        return this.getCategory().isPublishedBy(member);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
