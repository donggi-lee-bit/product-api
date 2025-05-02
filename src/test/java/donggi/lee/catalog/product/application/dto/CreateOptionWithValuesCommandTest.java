package donggi.lee.catalog.product.application.dto;

import donggi.lee.catalog.product.domain.OptionType;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreateOptionWithValuesCommandTest {

    @Test
    void 타입이_CUSTOM이고_커스텀값이_NULL이면_예외가_발생한다() {
        // given
        Long productId = 1L;
        String name = "Option Name";
        Long additionalPrice = 1000L;
        OptionType type = OptionType.CUSTOM;
        List<Long> definitionIds = Collections.emptyList();
        String customValue = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateOptionWithValuesCommand(productId, name, additionalPrice, type, definitionIds, customValue);
        });
    }

    @Test
    void 타입이_CUSTOM이고_커스텀값이_비어있으면_예외가_발생한다() {
        // given
        Long productId = 1L;
        String name = "Option Name";
        Long additionalPrice = 1000L;
        OptionType type = OptionType.CUSTOM;
        List<Long> definitionIds = Collections.emptyList();
        String customValue = "";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateOptionWithValuesCommand(productId, name, additionalPrice, type, definitionIds, customValue);
        });
    }

    @Test
    void 타입이_PREDEFINED이고_정의ID목록이_NULL이면_예외가_발생한다() {
        // given
        Long productId = 1L;
        String name = "Option Name";
        Long additionalPrice = 1000L;
        OptionType type = OptionType.PREDEFINED;
        List<Long> definitionIds = null;
        String customValue = "Custom Value";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateOptionWithValuesCommand(productId, name, additionalPrice, type, definitionIds, customValue);
        });
    }

    @Test
    void 타입이_PREDEFINED이고_정의ID목록이_비어있으면_예외가_발생한다() {
        // given
        Long productId = 1L;
        String name = "Option Name";
        Long additionalPrice = 1000L;
        OptionType type = OptionType.PREDEFINED;
        List<Long> definitionIds = Collections.emptyList();
        String customValue = "Custom Value";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateOptionWithValuesCommand(productId, name, additionalPrice, type, definitionIds, customValue);
        });
    }

    @Test
    void 타입이_CUSTOM이고_커스텀값이_제공되면_명령이_성공적으로_생성된다() {
        // given
        Long productId = 1L;
        String name = "Option Name";
        Long additionalPrice = 1000L;
        OptionType type = OptionType.CUSTOM;
        List<Long> definitionIds = Collections.emptyList();
        String customValue = "Custom Value";

        // when
        CreateOptionWithValuesCommand command = new CreateOptionWithValuesCommand(
                productId, name, additionalPrice, type, definitionIds, customValue);

        // then
        assertNotNull(command);
        assertEquals(productId, command.productId());
        assertEquals(name, command.name());
        assertEquals(additionalPrice, command.additionalPrice());
        assertEquals(type, command.type());
        assertEquals(definitionIds, command.definitionIds());
        assertEquals(customValue, command.customValue());
    }

    @Test
    void 타입이_PREDEFINED이고_정의ID목록이_제공되면_명령이_성공적으로_생성된다() {
        // given
        Long productId = 1L;
        String name = "Option Name";
        Long additionalPrice = 1000L;
        OptionType type = OptionType.PREDEFINED;
        List<Long> definitionIds = List.of(1L, 2L);
        String customValue = null;

        // when
        CreateOptionWithValuesCommand command = new CreateOptionWithValuesCommand(
                productId, name, additionalPrice, type, definitionIds, customValue);

        // then
        assertNotNull(command);
        assertEquals(productId, command.productId());
        assertEquals(name, command.name());
        assertEquals(additionalPrice, command.additionalPrice());
        assertEquals(type, command.type());
        assertEquals(definitionIds, command.definitionIds());
        assertEquals(customValue, command.customValue());
    }
}
