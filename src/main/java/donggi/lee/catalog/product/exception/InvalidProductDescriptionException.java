package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.DomainException;
import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;

public class InvalidProductDescriptionException extends DomainException {
    public InvalidProductDescriptionException() {
        super(
            ErrorCodeAndMessage.PRODUCT_INVALID_DESCRIPTION.getCode(),
            ErrorCodeAndMessage.PRODUCT_INVALID_DESCRIPTION.getMessage()
        );
    }
}
