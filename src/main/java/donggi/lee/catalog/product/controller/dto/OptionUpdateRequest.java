package donggi.lee.catalog.product.controller.dto;

import donggi.lee.catalog.product.domain.OptionType;
import donggi.lee.catalog.product.domain.ValueSource;

public record OptionUpdateRequest(
    // 상품 옵션 수정용
    String name,
    OptionType type,
    Long additionalPrice,

    // 옵션값 수정용
    String valueName,
    ValueSource source,
    Long definitionId
) { }
