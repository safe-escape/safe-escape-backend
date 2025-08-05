package team.safe.escape.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    final String code;
    final String message;
    final Object data;
    final LocalDateTime timestamp;

    private ErrorResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }


    public static ErrorResponse of(String code, String message, Object data) {
        return new ErrorResponse(code, message, data);
    }

}
