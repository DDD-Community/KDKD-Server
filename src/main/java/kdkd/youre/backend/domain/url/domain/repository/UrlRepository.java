package kdkd.youre.backend.domain.url.domain.repository;

import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long>, UrlCustomRepository {


    boolean existsByUrlAddressAndCategory_Member(String address, Member member);

    Url findByUrlAddressAndCategory_Member(String address, Member member);

    List<Url> findByCategory_Member(Member member);
}
