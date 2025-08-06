package team.safe.escape.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EXIT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 비상구입니다."),
    EMAIL_ALREADY_REGISTERED(HttpStatus.UNAUTHORIZED, "이미 가입된 이메일입니다."),
    INVALID_FORMAT_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    EMAIL_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않은 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
