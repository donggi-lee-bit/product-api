package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;
import donggi.lee.catalog.common.exception.NotFoundException;

public class OptionValueNotFoundException extends NotFoundException {
    public OptionValueNotFoundException(Long valueId) {
        super(
            ErrorCodeAndMessage.PRODUCT_OPTION_NOT_FOUND.getCode(),
            String.format(ErrorCodeAndMessage.PRODUCT_OPTION_NOT_FOUND.getMessage(), valueId)
        );
    }
}
