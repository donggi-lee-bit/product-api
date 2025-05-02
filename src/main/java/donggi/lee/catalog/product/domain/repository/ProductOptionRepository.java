package donggi.lee.catalog.product.domain.repository;

import donggi.lee.catalog.product.domain.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository {

    /**
     * 상품 옵션 저장
     * 
     * @param productOption 저장할 상품 옵션
     * @return 저장된 상품 옵션
     */
    ProductOption save(ProductOption productOption);

    /**
     * ID로 상품 옵션 조회
     * 
     * @param id 상품 옵션 ID
     * @return 상품 옵션 Optional
     */
    Optional<ProductOption> findById(Long id);

    /**
     * 모든 상품 옵션 페이징 조회
     * 
     * @param pageable 페이징 정보
     * @return 상품 옵션 페이지
     */
    Page<ProductOption> findAll(Pageable pageable);

    /**
     * 상품 ID로 상품 옵션 목록 조회
     * 
     * @param productId 상품 ID
     * @return 상품 옵션 목록
     */
    List<ProductOption> findByProductId(Long productId);

    /**
     * 상품 옵션 삭제
     * 
     * @param productOption 삭제할 상품 옵션
     */
    void delete(ProductOption productOption);

    /**
     * 상품 ID로 상품 옵션 개수 조회
     * 
     * @param productId 상품 ID
     * @return 상품 옵션 개수
     */
    long countByProductId(Long productId);
}
