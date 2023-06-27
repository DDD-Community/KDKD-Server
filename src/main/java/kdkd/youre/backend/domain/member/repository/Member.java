package kdkd.youre.backend.domain.member.repository;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname; // 회원 이름
    private String email; // 회원 이메일
    private String oauthProvider; // oAuth Provider (google)

    @Builder
    public Member(
            String nickname,
            String email,
            String oauthProvider) {
        this.nickname = nickname;
        this.email = email;
        this.oauthProvider = oauthProvider;
    }
}