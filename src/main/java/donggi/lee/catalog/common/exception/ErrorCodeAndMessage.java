package donggi.lee.catalog.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeAndMessage {

    PRODUCT_NOT_FOUND("P001", "상품을 찾을 수 없습니다: %d"),
    PRODUCT_OPTION_NOT_FOUND("P002", "상품 옵션을 찾을 수 없습니다: %d"),
    PRODUCT_OPTION_LIMIT_EXCEEDED("P003", "상품당 최대 %d개의 옵션만 추가할 수 있습니다. 현재: %d"),
    PRODUCT_INVALID_NAME("P004", "상품 이름은 필수입니다"),
    PRODUCT_INVALID_DESCRIPTION("P005", "상품 상세 설명은 필수입니다"),
    PRODUCT_INVALID_PRICE("P006", "상품 가격은 0보다 커야 합니다"),
    PRODUCT_INVALID_SHIPPING_FEE("P007", "배송비는 0 이상이어야 합니다"),

    USER_NOT_FOUND("U001", "사용자를 찾을 수 없습니다: %s"),
    EMAIL_DUPLICATION("U002", "이미 사용 중인 이메일입니다: %s"),
    INCORRECT_PASSWORD("U003", "비밀번호가 일치하지 않습니다"),

    UNKNOWN_ERROR("X000", "알 수 없는 서버 오류가 발생했습니다");

    private final String code;
    private final String message;

    ErrorCodeAndMessage(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
