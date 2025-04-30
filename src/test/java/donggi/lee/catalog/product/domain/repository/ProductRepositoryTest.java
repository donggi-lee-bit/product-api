package donggi.lee.catalog.product.domain.repository;

import donggi.lee.catalog.product.domain.Product;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 새로운_상품을_저장했을_때_저장된_상품을_ID로_조회할_수_있다() {
        // given
        Product product = new Product("", "", 100L, 3000L);

        // when
        Product saved = productRepository.save(product);

        // then
        Product found = productRepository.findById(saved.getId()).get();
        assertThat(found).isEqualTo(saved);
    }
}
