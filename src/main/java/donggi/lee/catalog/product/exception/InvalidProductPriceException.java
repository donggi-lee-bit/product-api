package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.DomainException;
import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;

public class InvalidProductPriceException extends DomainException {
    public InvalidProductPriceException() {
        super(
            ErrorCodeAndMessage.PRODUCT_INVALID_PRICE.getCode(),
            ErrorCodeAndMessage.PRODUCT_INVALID_PRICE.getMessage()
        );
    }
}
