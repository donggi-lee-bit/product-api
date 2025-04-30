package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.domain.Product;
import donggi.lee.catalog.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 새로운 상품을 생성하고 저장한다.
     */
    @Transactional
    public void create(String name, String description, long price, long shippingFee) {
        Product product = new Product(name, description, price, shippingFee);
        productRepository.save(product);
    }

    /**
     * 상품 ID로 단일 상품을 조회한다.
     */
    @Transactional(readOnly = true)
    public Product get(long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));
    }

    /**
     * 페이징 기준으로 상품 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public Page<Product> list(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    /**
     * 기존 상품 정보를 업데이트한다.
     */
    @Transactional
    public void update(long id, String name, String description, long price, long shippingFee) {
        Product product = get(id);
        product.changeDetails(name, description, price, shippingFee);
        productRepository.save(product);
    }

    /**
     * 주어진 ID의 상품을 삭제한다.
     */
    @Transactional
    public void delete(long id) {
        Product product = get(id);
        productRepository.delete(product);
    }
}
