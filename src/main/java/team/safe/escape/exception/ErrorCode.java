package team.safe.escape.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EXIT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 비상구입니다."),
    NEAR_EXIT_NOT_FOUND(HttpStatus.NOT_FOUND, "가까운 비상구를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    SHELTER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 대피소입니다."),
    SHELTER_BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "찜한 대피소가 존재하지 않습니다."),
    EXIST_SHELTER_BOOKMARK(HttpStatus.BAD_REQUEST, "이미 찜한 대피소입니다."),
    EMAIL_ALREADY_REGISTERED(HttpStatus.UNAUTHORIZED, "이미 가입된 이메일입니다."),
    INVALID_FORMAT_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    EMAIL_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않은 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다."),
    TOKEN_USER_MISMATCH(HttpStatus.UNAUTHORIZED, "accessToken과 refreshToken의 사용자가 일치하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 refresh 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 refreshToken 입니다."),
    ALREADY_SAVE_SHELTER(HttpStatus.CONFLICT, "이미 대피소를 저장했습니다."),
    ALREADY_SAVE_POPULATION_AREA(HttpStatus.CONFLICT, "이미 인구 지역을 저장했습니다."),
    CSV_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 파일 처리 중 오류가 발생했습니다."),
    EXIT_MINIMUM_REQUIRED(HttpStatus.BAD_REQUEST, "비상구는 2개 이상부터 지정할 수 있습니다."),
    CROWDED_MINIMUM_REQUIRED(HttpStatus.BAD_REQUEST, "혼잡지역은 좌표 2개 이상부터 지정할 수 있습니다."),
    INVALID_LOCATION(HttpStatus.BAD_REQUEST, "경도, 위도 값이 잘못되었습니다.")
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
