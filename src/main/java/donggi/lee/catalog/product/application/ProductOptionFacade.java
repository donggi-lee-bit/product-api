package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.application.dto.CreateOptionWithValuesCommand;
import donggi.lee.catalog.product.application.dto.UpdateOptionValueCommand;
import donggi.lee.catalog.product.application.dto.UpdateProductOptionCommand;
import donggi.lee.catalog.product.domain.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 옵션과 옵션 값을 함께 등록, 수정하는
 * 유스케이스 전용 애플리케이션 서비스
 */
@Service
@RequiredArgsConstructor
public class ProductOptionFacade {

    private final ProductOptionService productOptionService;
    private final OptionValueService optionValueService;

    /**
     * 상품에 새로운 옵션을 추가하고, 옵션값도 함께 등록한다.
     *
     * @param request 옵션명, 타입, 가격, 그리고 옵션값 정보 포함
     * @return 생성된 ProductOption 엔티티
     */
    @Transactional
    public ProductOption createOptionWithValues(CreateOptionWithValuesCommand request) {
        // 상품 옵션 생성
        ProductOption option = productOptionService.create(
            request.productId(),
            request.name(),
            request.type(),
            request.additionalPrice()
        );

        // 옵션값 생성
        optionValueService.create(option.getId(), request.value());

        return option;
    }

    /**
     * 상품 옵션을 수정하고, 옵션값도 함께 수정한다.
     *
     * @param optionId 옵션 ID
     * @param optionCommand 옵션 수정 정보
     * @param valueCommand 옵션값 수정 정보
     */
    @Transactional
    public void updateOptionWithValues(Long optionId, UpdateProductOptionCommand optionCommand, UpdateOptionValueCommand valueCommand) {
        // 옵션 갱신
        productOptionService.update(optionId, optionCommand);

        // 옵션값 갱신
        optionValueService.update(optionId, valueCommand);
    }
}
