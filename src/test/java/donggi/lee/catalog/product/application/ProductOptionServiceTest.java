package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.domain.OptionType;
import donggi.lee.catalog.product.domain.Product;
import donggi.lee.catalog.product.domain.repository.ProductOptionRepository;
import donggi.lee.catalog.product.domain.repository.ProductRepository;
import donggi.lee.catalog.product.exception.ProductOptionLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductOptionServiceTest {

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    private Product savedProduct;

    @BeforeEach
    void setup() {
        // 각 테스트 실행 전 새로운 상품을 준비
        savedProduct = productRepository.save(new Product("테스트 상품", "기본 상품 설명", 10000L, 3000L));

    }

    @Nested
    class 옵션_생성_시 {

        @Nested
        class 옵션_개수가_최대_개수_미만일_때 {

            @Test
            void 새로운_옵션이_정상적으로_DB에_저장되어야_한다() {
                // given
                final var newOptionName = "추가 옵션";
                final var newOptionType = OptionType.INPUT;
                final var newAdditionalPrice = 1000L;

                // when
                final var createdOption = productOptionService.create(savedProduct.getId(), newOptionName, newOptionType, newAdditionalPrice);

                // then
                assertThat(createdOption).isNotNull();
                assertThat(createdOption.getId()).isNotNull();
                assertThat(createdOption.getProductId()).isEqualTo(savedProduct.getId());
                assertThat(createdOption.getName()).isEqualTo(newOptionName);
                assertThat(createdOption.getType()).isEqualTo(newOptionType);
                assertThat(createdOption.getAdditionalPrice()).isEqualTo(newAdditionalPrice);

                final var foundOption = productOptionRepository.findById(createdOption.getId()).orElseThrow();
                assertThat(foundOption.getName()).isEqualTo(newOptionName); // 저장된 값 일치 확인
                assertThat(productOptionRepository.countByProductId(savedProduct.getId())).isEqualTo(1);
            }
        }

        @Nested
        class 옵션_개수가_최대_개수를_초과했을_때 {
            @Test
            void ProductOptionLimitExceededException_예외를_반환한다() {
                // given
                final var extraOptionName = "추가 불가 옵션";
                final var extraOptionType = OptionType.INPUT;
                final var extraAdditionalPrice = 1000L;
                productOptionService.create(savedProduct.getId(), extraOptionName, extraOptionType, extraAdditionalPrice);
                productOptionService.create(savedProduct.getId(), extraOptionName, extraOptionType, extraAdditionalPrice);
                productOptionService.create(savedProduct.getId(), extraOptionName, extraOptionType, extraAdditionalPrice);

                // when & then
                assertThatThrownBy(() -> productOptionService.create(savedProduct.getId(), extraOptionName, extraOptionType, extraAdditionalPrice))
                    .isInstanceOf(ProductOptionLimitExceededException.class);
            }
        }

    }
}
