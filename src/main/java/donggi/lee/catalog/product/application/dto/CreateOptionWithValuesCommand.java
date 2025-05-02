package donggi.lee.catalog.product.application.dto;

import donggi.lee.catalog.product.domain.OptionType;

import java.util.List;

public record CreateOptionWithValuesCommand(
    Long productId,
    String name,
    Long additionalPrice,
    OptionType type,
    List<Long> definitionIds, // PREDEFINED 용
    String customValue        // CUSTOM 용
) {
    public CreateOptionWithValuesCommand {
        if (type == OptionType.CUSTOM && (customValue == null || customValue.isBlank())) {
            throw new IllegalArgumentException("CUSTOM 타입 옵션은 customValue가 있어야 합니다");
        }

        if (type == OptionType.PREDEFINED && (definitionIds == null || definitionIds.isEmpty())) {
            throw new IllegalArgumentException("PREDEFINED 타입 옵션은 definitionIds가 있어야 합니다");
        }
    }
}
