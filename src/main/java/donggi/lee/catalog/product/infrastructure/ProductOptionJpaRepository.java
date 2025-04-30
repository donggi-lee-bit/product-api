package donggi.lee.catalog.product.infrastructure;

import donggi.lee.catalog.product.domain.ProductOption;
import donggi.lee.catalog.product.domain.repository.ProductOptionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOption, Long>, ProductOptionRepository {
}
