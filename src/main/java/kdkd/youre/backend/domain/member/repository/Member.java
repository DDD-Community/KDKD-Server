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

    private String nickname;
    private String email;
    private String oauthProvider;

    @Builder
    public Member(
            Long id,
            String nickname,
            String email,
            String oauthProvider) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.oauthProvider = oauthProvider;
    }
}