package kdkd.youre.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //공통 예외
    BAD_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    BAD_REQUEST_VALIDATION(HttpStatus.BAD_REQUEST, "검증에 실패하였습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    // Member 예외
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    UNAUTHORIZED_ID(HttpStatus.UNAUTHORIZED, "아이디가 틀립니다."),
    UNAUTHORIZED_PASSWORD(HttpStatus.UNAUTHORIZED, "패스워드가 틀립니다."),
    FORBIDDEN_MEMBER(HttpStatus.UNAUTHORIZED, "해당 자원에 대한 접근 권한이 없습니다."),

    // Url 예외
    NOT_FOUND_URL(HttpStatus.NOT_FOUND, "해당 url을 찾을 수 없습니다."),

    // Tag 예외
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "해당 태그를 찾을 수 없습니다."),

    // Category 예외
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),
    CONFLICT_CATEGORY(HttpStatus.CONFLICT, "해당 카테고리가 중복됩니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
