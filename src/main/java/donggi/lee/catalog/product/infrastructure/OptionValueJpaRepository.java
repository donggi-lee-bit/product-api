package donggi.lee.catalog.product.infrastructure;

import donggi.lee.catalog.product.domain.OptionValue;
import donggi.lee.catalog.product.domain.repository.OptionValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionValueJpaRepository extends JpaRepository<OptionValue, Long>, OptionValueRepository {
}
