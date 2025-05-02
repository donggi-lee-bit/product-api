package donggi.lee.catalog.product.application.dto;

import donggi.lee.catalog.product.domain.OptionType;

public record UpdateProductOptionCommand(
    String name,
    Long additionalPrice,
    OptionType type
) {
}
