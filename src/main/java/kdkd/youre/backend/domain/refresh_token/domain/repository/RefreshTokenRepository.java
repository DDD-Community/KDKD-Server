package kdkd.youre.backend.domain.refresh_token.domain.repository;

import kdkd.youre.backend.domain.refresh_token.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByKeyLoginId(String loginId);
    void deleteByKeyLoginId(String loginId);
}
