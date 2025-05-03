package donggi.lee.catalog.common.exception;

/**
 * 모든 비즈니스 예외의 최상위 추상 클래스
 */
public abstract class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message);
    }
    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
