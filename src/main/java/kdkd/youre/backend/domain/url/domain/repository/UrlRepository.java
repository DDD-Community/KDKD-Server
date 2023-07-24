package kdkd.youre.backend.domain.url.domain.repository;

import kdkd.youre.backend.domain.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

//    url 검사
//    중복 true 중복x false
    boolean existsByUrl(String Url);
}
