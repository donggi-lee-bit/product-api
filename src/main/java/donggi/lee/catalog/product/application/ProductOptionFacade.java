package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.application.dto.CreateOptionWithValuesCommand;
import donggi.lee.catalog.product.application.dto.UpdateOptionValueCommand;
import donggi.lee.catalog.product.application.dto.UpdateProductOptionCommand;
import donggi.lee.catalog.product.domain.OptionType;
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

        // 옵션 타입에 따라 옵션값 생성
        if (request.type() == OptionType.PREDEFINED) {
            // 사전 정의값 ID 목록을 받아서 반복 생성
            for (Long definitionId : request.definitionIds()) {
                optionValueService.createPredefined(option.getId(), definitionId);
            }
        } else {
            optionValueService.createManual(option.getId(), request.customValue());
        }

        return option;
    }

    @Transactional
    public void updateOptionWithValues(Long optionId, UpdateProductOptionCommand optionCommand, UpdateOptionValueCommand valueCommand) {
        // 옵션 갱신
        productOptionService.update(optionId, optionCommand);

        // 옵션값 갱신
        optionValueService.update(optionId, valueCommand);
    }
}
