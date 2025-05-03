
package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.application.dto.UpdateProductOptionCommand;
import donggi.lee.catalog.product.domain.OptionType;
import donggi.lee.catalog.product.domain.ProductOption;
import donggi.lee.catalog.product.domain.repository.ProductOptionRepository;
import donggi.lee.catalog.product.exception.ProductOptionLimitExceededException;
import donggi.lee.catalog.product.exception.ProductOptionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
    
    private final ProductOptionRepository productOptionRepository;

    private static final int MAX_OPTIONS_PER_PRODUCT = 3;

    @Transactional
    public ProductOption create(final Long productId, final String name, final OptionType type, final Long additionalPrice) {
        // 상품당 옵션 개수 제한 검증 (최대 3개)
        final var currentCount = productOptionRepository.countByProductId(productId);
        if (currentCount >= MAX_OPTIONS_PER_PRODUCT) {
            throw new ProductOptionLimitExceededException(productId, currentCount);
        }

        final var option = new ProductOption(productId, name, type, additionalPrice);
        
        return productOptionRepository.save(option);
    }

    @Transactional(readOnly = true)
    public ProductOption getById(final Long id) {
        return productOptionRepository.findById(id)
            .orElseThrow(() -> new ProductOptionNotFoundException(id));
    }
    
    @Transactional(readOnly = true)
    public Page<ProductOption> list(final Pageable pageable) {
        return productOptionRepository.findAll(pageable);
    }
    
    @Transactional
    public void update(final Long id, final UpdateProductOptionCommand optionCommand) {
        final var option = getById(id);

        option.update(optionCommand.name(), optionCommand.additionalPrice(), optionCommand.type());
    }
    
    @Transactional
    public void delete(final Long id) {
        ProductOption option = getById(id);
        productOptionRepository.delete(option);
    }
}
