package kdkd.youre.backend.domain.refresh_token.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;
    private String refreshToken;
    private String keyLoginId;

    @Builder
    public RefreshToken (String refreshToken, String keyLoginId) {
        this.refreshToken = refreshToken;
        this.keyLoginId = keyLoginId;
    }
}
