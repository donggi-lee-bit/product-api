package donggi.lee.catalog.product.infrastructure;

import donggi.lee.catalog.product.domain.Product;
import donggi.lee.catalog.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {
}
