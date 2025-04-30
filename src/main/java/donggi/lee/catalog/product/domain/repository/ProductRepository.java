package donggi.lee.catalog.product.domain.repository;

import donggi.lee.catalog.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    /**
     * 주어진 상품 엔티티를 저장합니다.
     *
     * @param product 저장할 상품 엔티티
     * @return 저장된 상품 엔티티
     */
    Product save(Product product);

    /**
     * 주어진 식별자에 해당하는 상품을 조회합니다.
     *
     * @param id 조회할 상품의 고유 식별자
     * @return 해당 ID를 가진 상품이 존재하면 Optional에 담아 반환하고,
     *         존재하지 않으면 빈 Optional을 반환합니다.
     */
    Optional<Product> findById(long id);
}
