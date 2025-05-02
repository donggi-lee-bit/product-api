package donggi.lee.catalog.product.infrastructure;

import donggi.lee.catalog.product.domain.OptionValueDefinition;
import donggi.lee.catalog.product.domain.repository.OptionValueDefinitionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionValueDefinitionJpaRepository extends JpaRepository<OptionValueDefinition, Long>, OptionValueDefinitionRepository {

    Optional<OptionValueDefinition> findByCode(String code);
}
