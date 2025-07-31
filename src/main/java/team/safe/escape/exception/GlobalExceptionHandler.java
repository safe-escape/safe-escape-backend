package team.safe.escape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EscapeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handlerEscapeException(EscapeException ex) {
        ErrorCode code = ex.getCode();
        ErrorResponse errorResponse = ErrorResponse.of(code.getStatus().value(), code.getMessage(), ex.getData());

        return ResponseEntity
                .status(code.getStatus())
                .body(errorResponse);
    }

}
