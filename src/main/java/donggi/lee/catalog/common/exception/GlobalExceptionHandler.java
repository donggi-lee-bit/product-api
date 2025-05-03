package donggi.lee.catalog.common.exception;

import donggi.lee.catalog.common.exception.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException exception) {
        log.info("[404 Not Found] 에러코드={}, 메시지={}", exception.getErrorCode(), exception.getMessage());
        return ErrorResponse.from(exception);
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDomainException(final DomainException exception) {
        log.warn("[400 Bad Request] 도메인 예외 발생 - 코드={}, 메시지={}", exception.getErrorCode(), exception.getMessage());
        return ErrorResponse.from(exception);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBusinessException(final BusinessException exception) {
        log.warn("[400 Bad Request] 비즈니스 예외 발생 - 코드={}, 메시지={}", exception.getErrorCode(), exception.getMessage());
        return ErrorResponse.from(exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(final Exception exception) {
        log.error("[500 Internal Server Error] 알 수 없는 예외 발생", exception);
        return new ErrorResponse(
            ErrorCodeAndMessage.UNKNOWN_ERROR.getCode(),
            ErrorCodeAndMessage.UNKNOWN_ERROR.getMessage()
        );
    }
}
