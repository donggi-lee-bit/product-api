package donggi.lee.catalog.product.application.dto;

import donggi.lee.catalog.product.domain.ValueSource;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UpdateOptionValueCommandTest {

    @Test
    void 타입이_MANUAL이고_valueName이_NULL이면_예외가_발생한다() {
        // given
        String valueName = null;
        ValueSource source = ValueSource.MANUAL;
        Long definitionId = null;

        // when
        var actual = assertThatThrownBy(() ->
            new UpdateOptionValueCommand(valueName, source, definitionId)
        );

        // then
        actual.isInstanceOf(IllegalArgumentException.class)
            .hasMessage("MANUAL 타입 옵션 수정 시 valueName을 입력해야 합니다.");
    }

    @Test
    void 타입이_MANUAL이고_valueName이_빈문자열이면_예외가_발생한다() {
        // given
        String valueName = "   ";
        ValueSource source = ValueSource.MANUAL;
        Long definitionId = null;

        // when
        var actual = assertThatThrownBy(() ->
            new UpdateOptionValueCommand(valueName, source, definitionId)
        );

        // then
        actual.isInstanceOf(IllegalArgumentException.class)
            .hasMessage("MANUAL 타입 옵션 수정 시 valueName을 입력해야 합니다.");
    }

    @Test
    void 타입이_MANUAL이고_definitionId가_존재하면_예외가_발생한다() {
        // given
        String valueName = "사용자입력";
        ValueSource source = ValueSource.MANUAL;
        Long definitionId = 123L;

        // when
        var actual = assertThatThrownBy(() ->
            new UpdateOptionValueCommand(valueName, source, definitionId)
        );

        // then
        actual.isInstanceOf(IllegalArgumentException.class)
            .hasMessage("MANUAL 타입 옵션 수정 시 definitionId를 사용할 수 없습니다.");
    }

    @Test
    void 타입이_MANUAL이고_valueName이_존재하면_정상_생성된다() {
        // given
        String valueName = "사용자입력값";
        ValueSource source = ValueSource.MANUAL;
        Long definitionId = null;

        // when
        UpdateOptionValueCommand actual =
            new UpdateOptionValueCommand(valueName, source, definitionId);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.valueName()).isEqualTo("사용자입력값");
        assertThat(actual.source()).isEqualTo(ValueSource.MANUAL);
        assertThat(actual.definitionId()).isNull();
    }

    @Test
    void 타입이_PREDEFINED이고_definitionId가_NULL이면_예외가_발생한다() {
        // given
        String valueName = null;
        ValueSource source = ValueSource.PREDEFINED;
        Long definitionId = null;

        // when
        var actual = assertThatThrownBy(() ->
            new UpdateOptionValueCommand(valueName, source, definitionId)
        );

        // then
        actual.isInstanceOf(IllegalArgumentException.class)
            .hasMessage("PREDEFINED 타입 옵션 수정 시 definitionId를 입력해야 합니다.");
    }

    @Test
    void 타입이_PREDEFINED이고_valueName이_존재하면_예외가_발생한다() {
        // given
        String valueName = "잘못된입력";
        ValueSource source = ValueSource.PREDEFINED;
        Long definitionId = 456L;

        // when
        var actual = assertThatThrownBy(() ->
            new UpdateOptionValueCommand(valueName, source, definitionId)
        );

        // then
        actual.isInstanceOf(IllegalArgumentException.class)
            .hasMessage("PREDEFINED 타입 옵션 수정 시 valueName을 사용할 수 없습니다.");
    }

    @Test
    void 타입이_PREDEFINED이고_definitionId가_존재하면_정상_생성된다() {
        // given
        String valueName = null;
        ValueSource source = ValueSource.PREDEFINED;
        Long definitionId = 789L;

        // when
        UpdateOptionValueCommand actual =
            new UpdateOptionValueCommand(valueName, source, definitionId);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.valueName()).isNull();
        assertThat(actual.source()).isEqualTo(ValueSource.PREDEFINED);
        assertThat(actual.definitionId()).isEqualTo(789L);
    }
}
