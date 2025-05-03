package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;
import donggi.lee.catalog.common.exception.NotFoundException;

public class ProductOptionNotFoundException extends NotFoundException {

    public ProductOptionNotFoundException(final Long optionId) {
        super(String.format(ErrorCodeAndMessage.PRODUCT_OPTION_NOT_FOUND.getMessage(), optionId));
    }
}
