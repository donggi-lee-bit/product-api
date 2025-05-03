package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.DomainException;
import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;

public class InvalidShippingFeeException extends DomainException {
    public InvalidShippingFeeException() {
        super(
            ErrorCodeAndMessage.PRODUCT_INVALID_SHIPPING_FEE.getCode(),
            ErrorCodeAndMessage.PRODUCT_INVALID_SHIPPING_FEE.getMessage()
        );
    }
}
