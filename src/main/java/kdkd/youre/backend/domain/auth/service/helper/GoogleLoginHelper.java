package kdkd.youre.backend.domain.auth.service.helper;

import kdkd.youre.backend.domain.auth.presentation.dto.GoogleAuthDto;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleLoginHelper {

    public Member getUserData(String idToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<GoogleAuthDto> response = restTemplate.getForEntity(
                    "https://oauth2.googleapis.com/tokeninfo?id_token={idToken}",
                    GoogleAuthDto.class,
                    idToken
            );

            GoogleAuthDto googleAuthDto = response.getBody();

            return Member.builder()
                    .nickname(googleAuthDto.getName())
                    .email(googleAuthDto.getEmail())
                    .oauthProvider("GOOGLE")
                    .loginId(googleAuthDto.getSub())
                    .password("") // TODO: null도 괜찮다면 제거하기
                    .role("ROLE_USER") // TODO: 추후 ENUM으로 관리하기
                    .build();
        } catch (HttpClientErrorException e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_TOKEN);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
