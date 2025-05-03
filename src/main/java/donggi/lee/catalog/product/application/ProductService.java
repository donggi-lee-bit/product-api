package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.domain.Product;
import donggi.lee.catalog.product.domain.repository.ProductRepository;
import donggi.lee.catalog.product.exception.ProductNotFoundException;
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
    public void create(final String name, final String description, final long price, final long shippingFee) {
        final var product = new Product(name, description, price, shippingFee);

        productRepository.save(product);
    }

    /**
     * 상품 ID로 단일 상품을 조회한다.
     */
    @Transactional(readOnly = true)
    public Product get(final long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 페이징 기준으로 상품 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public Page<Product> list(final PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    /**
     * 기존 상품 정보를 업데이트한다.
     */
    @Transactional
    public void update(final long id, final String name, final String description, final long price, final long shippingFee) {
        final var product = get(id);

        product.changeDetails(name, description, price, shippingFee);

        productRepository.save(product);
    }

    /**
     * 주어진 ID의 상품을 삭제한다.
     */
    @Transactional
    public void delete(final long id) {
        final var product = get(id);

        productRepository.delete(product);
    }
}
