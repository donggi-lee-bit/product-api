package donggi.lee.catalog.product.controller.dto;

import donggi.lee.catalog.product.domain.OptionType;
import donggi.lee.catalog.product.domain.ProductOption;

public record ProductOptionResponse(
    Long id,
    Long productId,
    String name,
    OptionType type,
    Long additionalPrice
) {
    public static ProductOptionResponse from(ProductOption option) {
        return new ProductOptionResponse(
            option.getId(),
            option.getProductId(),
            option.getName(),
            option.getType(),
            option.getAdditionalPrice()
        );
    }
}
