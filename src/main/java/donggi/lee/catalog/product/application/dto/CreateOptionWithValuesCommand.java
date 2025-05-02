package donggi.lee.catalog.product.application.dto;

import donggi.lee.catalog.product.domain.OptionType;

public record CreateOptionWithValuesCommand(
    Long productId,
    String name,
    Long additionalPrice,
    OptionType type,
    String value
) { }
