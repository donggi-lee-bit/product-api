
package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.domain.OptionType;
import donggi.lee.catalog.product.domain.ProductOption;
import donggi.lee.catalog.product.domain.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
    
    private final ProductOptionRepository productOptionRepository;
    
    @Transactional
    public ProductOption create(Long productId, String name, OptionType type, Long additionalPrice) {
        // 상품당 옵션 개수 제한 검증 (최대 3개)
        long optionCount = productOptionRepository.countByProductId(productId);
        if (optionCount >= 3) {
            throw new IllegalStateException("상품당 최대 3개의 옵션만 추가할 수 있습니다");
        }

        ProductOption option = new ProductOption(productId, name, type, additionalPrice);
        
        return productOptionRepository.save(option);
    }

    @Transactional(readOnly = true)
    public ProductOption getById(Long id) {
        return productOptionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품 옵션을 찾을 수 없습니다: " + id));
    }
    
    @Transactional(readOnly = true)
    public Page<ProductOption> list(Pageable pageable) {
        return productOptionRepository.findAll(pageable);
    }
    
    @Transactional
    public ProductOption update(Long id, String name, Long additionalPrice, OptionType type) {
        ProductOption option = getById(id);

        option.update(name, additionalPrice, type);
        
        return option;
    }
    
    @Transactional
    public void delete(Long id) {
        ProductOption option = getById(id);
        productOptionRepository.delete(option);
    }
}
