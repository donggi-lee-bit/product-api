package donggi.lee.catalog.common.exception;

import lombok.Getter;

/**
 * 모든 비즈니스 예외의 최상위 추상 클래스
 */
@Getter
public abstract class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(final String errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(final String errorCode, final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
