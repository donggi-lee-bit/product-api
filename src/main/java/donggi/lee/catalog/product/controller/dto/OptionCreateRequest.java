package donggi.lee.catalog.product.controller.dto;

import donggi.lee.catalog.product.domain.OptionType;

import java.util.List;

public record OptionCreateRequest(
    Long productId,
    String name,
    OptionType type,
    Long additionalPrice,
    List<Long> definitionIds, // PREDEFINED 용
    String customValue        // MANUAL 용
) { }
