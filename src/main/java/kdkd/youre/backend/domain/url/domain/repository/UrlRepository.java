package kdkd.youre.backend.domain.url.domain.repository;

import kdkd.youre.backend.domain.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
    boolean existsByUrl(String Url);
}
