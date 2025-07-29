package team.safe.escape.exception;

import lombok.Getter;

@Getter
public class EscapeException extends RuntimeException {

    private final ErrorCode code;
    private final transient Object data;

    public EscapeException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
        this.data = null;
    }

    public EscapeException(ErrorCode code, Object data) {
        super(code.getMessage());
        this.code = code;
        this.data = data;
    }

}
