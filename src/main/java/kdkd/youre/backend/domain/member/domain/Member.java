package kdkd.youre.backend.domain.member.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // 회원 기본 정보
    private String nickname; // 회원 닉네임
    private String email; // 회원 이메일

    // 소셜 로그인 정보
    private String oauthProvider; // oAuth Provider (google)
    private String loginId; // 로컬 로그인용. username 역할
    private String password; // 로컬 로그인용
    private String role;

    @Builder
    public Member(
            String nickname,
            String email,
            String oauthProvider,
            String loginId,
            String password,
            String role ) {
        this.nickname = nickname;
        this.email = email;
        this.oauthProvider = oauthProvider;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }
}