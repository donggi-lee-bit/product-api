package donggi.lee.catalog.product.controller.dto;

import donggi.lee.catalog.product.domain.OptionType;

public record OptionUpdateRequest(
    String name,
    OptionType type,
    Long additionalPrice
) { }
