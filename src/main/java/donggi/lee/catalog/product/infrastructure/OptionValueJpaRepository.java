package donggi.lee.catalog.product.infrastructure;

import donggi.lee.catalog.product.domain.OptionValue;
import donggi.lee.catalog.product.domain.repository.OptionValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionValueJpaRepository extends JpaRepository<OptionValue, Long>, OptionValueRepository {
    
    List<OptionValue> findByOptionId(Long optionId);

    void deleteAllByOptionId(Long optionId);
}
