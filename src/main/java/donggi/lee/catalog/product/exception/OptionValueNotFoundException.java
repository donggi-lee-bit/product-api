package donggi.lee.catalog.product.exception;

import donggi.lee.catalog.common.exception.NotFoundException;

public class OptionValueNotFoundException extends NotFoundException {
    public OptionValueNotFoundException(Long valueId) {
        super("옵션 값을 찾을 수 없습니다: " + valueId);
    }
}
