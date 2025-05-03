package donggi.lee.catalog.common.exception;

/**
 * 도메인 규칙 위반 등, 상태가 유효하지 않을 때 던지는 예외의 공통 추상 클래스
 */
public abstract class DomainException extends BusinessException {
  public DomainException(final String message) {
    super(message);
  }
}
