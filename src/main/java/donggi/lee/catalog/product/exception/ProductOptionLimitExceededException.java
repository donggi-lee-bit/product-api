package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.DomainException;
import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;

public class ProductOptionLimitExceededException extends DomainException {

    public ProductOptionLimitExceededException(final Long productId, final Long currentCount) {
        super(
            ErrorCodeAndMessage.PRODUCT_OPTION_LIMIT_EXCEEDED.getCode(),
            String.format(ErrorCodeAndMessage.PRODUCT_OPTION_LIMIT_EXCEEDED.getMessage(), 3, currentCount)
        );
    }
}
