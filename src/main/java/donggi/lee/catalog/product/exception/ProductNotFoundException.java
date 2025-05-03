package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;
import donggi.lee.catalog.common.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException(final Long productId) {
        super(
            ErrorCodeAndMessage.PRODUCT_NOT_FOUND.getCode(),
            String.format(ErrorCodeAndMessage.PRODUCT_NOT_FOUND.getMessage(), productId)
        );
    }
}
