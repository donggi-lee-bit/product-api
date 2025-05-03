package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.DomainException;
import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;

public class InvalidProductNameException extends DomainException {
    public InvalidProductNameException() {
        super(
            ErrorCodeAndMessage.PRODUCT_INVALID_NAME.getCode(),
            ErrorCodeAndMessage.PRODUCT_INVALID_NAME.getMessage()
        );
    }
}
