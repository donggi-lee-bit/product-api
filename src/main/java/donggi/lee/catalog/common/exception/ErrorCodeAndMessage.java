package donggi.lee.catalog.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeAndMessage {

    PRODUCT_NOT_FOUND( "P001", "상품을 찾을 수 없습니다: %d" ),
    PRODUCT_OPTION_NOT_FOUND( "P002", "상품 옵션을 찾을 수 없습니다: %d" ),
    PRODUCT_OPTION_LIMIT_EXCEEDED( "P003", "상품당 최대 %d개의 옵션만 추가할 수 있습니다. 현재: %d" )
    ;

    private final String code;
    private final String message;

    ErrorCodeAndMessage(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
