package donggi.lee.catalog.product.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OptionValueTest {

    @Nested
    class 옵션값_생성_시 {

        @Nested
        class 입력_타입일_경우 {

            @Test
            void 정의_참조_없이_생성하면_성공한다() {
                // given
                Long optionId = 1L;
                String valueName = "커스텀 색상";
                ValueSource source = ValueSource.MANUAL;

                // when
                OptionValue value = new OptionValue(optionId, valueName, source, null);

                // then
                assertThat(value.getOptionId()).isEqualTo(optionId);
                assertThat(value.getValueName()).isEqualTo(valueName);
                assertThat(value.getSource()).isEqualTo(source);
                assertThat(value.getDefinition()).isNull();
            }

            @Test
            void 정의_참조가_있으면_예외가_발생한다() {
                // given
                Long optionId = 1L;
                String valueName = "커스텀 색상";
                ValueSource source = ValueSource.MANUAL;
                OptionValueDefinition definition = new OptionValueDefinition("COLOR_RED", "빨강", 1);

                // when & then
                assertThatThrownBy(() -> new OptionValue(optionId, valueName, source, definition))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("MANUAL 소스 옵션 값은 정의 참조를 가질 수 없습니다");
            }
        }

        @Nested
        class 선택_타입일_경우 {

            @Test
            void 정의_참조와_함께_생성하면_성공한다() {
                // given
                Long optionId = 1L;
                String valueName = "빨강";
                ValueSource source = ValueSource.PREDEFINED;
                OptionValueDefinition definition = new OptionValueDefinition("COLOR_RED", "빨강", 1);

                // when
                OptionValue value = new OptionValue(optionId, valueName, source, definition);

                // then
                assertThat(value.getOptionId()).isEqualTo(optionId);
                assertThat(value.getValueName()).isEqualTo(valueName);
                assertThat(value.getSource()).isEqualTo(source);
                assertThat(value.getDefinition()).isEqualTo(definition);
            }

            @Test
            void 정의_참조_없이_생성하면_예외가_발생한다() {
                // given
                Long optionId = 1L;
                String valueName = "빨강";
                ValueSource source = ValueSource.PREDEFINED;

                // when & then
                assertThatThrownBy(() -> new OptionValue(optionId, valueName, source, null))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("PREDEFINED 소스 옵션 값은 유효한 정의 참조가 필요합니다");
            }
        }
    }

    @Nested
    class 옵션값_업데이트_시 {

        @Test
        void 같은_타입으로_업데이트하면_성공한다() {
            // given
            OptionValue value = new OptionValue(1L, "커스텀 색상", ValueSource.MANUAL, null);
            String updatedValueName = "새 커스텀 색상";

            // when
            value.updateCustomValue(updatedValueName);

            // then
            assertThat(value.getValueName()).isEqualTo(updatedValueName);
            assertThat(value.getSource()).isEqualTo(ValueSource.MANUAL);
            assertThat(value.getDefinition()).isNull();
        }

        @Test
        void 입력_타입에서_선택_타입으로_변경하면_성공한다() {
            // given
            OptionValue value = new OptionValue(1L, "커스텀 색상", ValueSource.MANUAL, null);
            OptionValueDefinition definition = new OptionValueDefinition("COLOR_RED", "빨강", 1);

            // when
            value.updatePredefinedValue(definition);

            // then
            assertThat(value.getValueName()).isEqualTo("빨강");
            assertThat(value.getSource()).isEqualTo(ValueSource.PREDEFINED);
            assertThat(value.getDefinition()).isEqualTo(definition);
        }

        @Test
        void 선택_타입에서_입력_타입으로_변경하면_성공한다() {
            // given
            OptionValueDefinition definition = new OptionValueDefinition("COLOR_RED", "빨강", 1);
            OptionValue value = new OptionValue(1L, "빨강", ValueSource.PREDEFINED, definition);

            // when
            value.updateCustomValue("새 커스텀 색상");

            // then
            assertThat(value.getValueName()).isEqualTo("새 커스텀 색상");
            assertThat(value.getSource()).isEqualTo(ValueSource.MANUAL);
            assertThat(value.getDefinition()).isNull();
        }
    }
}
