package donggi.lee.catalog.common.exception;

/**
 * 리소스를 찾지 못했을 때 던지는 예외의 공통 추상 클래스
 */
public abstract class NotFoundException extends BusinessException {
    public NotFoundException(final String code, final String message) {
        super(code, message);
    }
}
