package donggi.lee.catalog.product.controller;

import donggi.lee.catalog.product.application.ProductOptionFacade;
import donggi.lee.catalog.product.application.ProductOptionService;
import donggi.lee.catalog.product.application.dto.CreateOptionWithValuesCommand;
import donggi.lee.catalog.product.application.dto.UpdateOptionValueCommand;
import donggi.lee.catalog.product.application.dto.UpdateProductOptionCommand;
import donggi.lee.catalog.product.controller.dto.OptionCreateRequest;
import donggi.lee.catalog.product.controller.dto.OptionUpdateRequest;
import donggi.lee.catalog.product.controller.dto.ProductOptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 옵션", description = "상품 옵션 관리 API")
@RestController
@RequestMapping("/v1/product-options")
@RequiredArgsConstructor
public class ProductOptionRestController {

    private final ProductOptionService productOptionService;
    private final ProductOptionFacade productOptionFacade;

    @Operation(summary = "옵션 생성", description = "상품에 새로운 옵션과 옵션 값을 등록합니다")
    @PostMapping
    public ProductOptionResponse create(@RequestBody final OptionCreateRequest request) {
        final var command = new CreateOptionWithValuesCommand(
            request.productId(),
            request.name(),
            request.additionalPrice(),
            request.type(),
            request.value()
        );
        final var createdOption = productOptionFacade.createOptionWithValues(command);
        return ProductOptionResponse.from(createdOption);
    }

    @Operation(summary = "옵션 단건 조회", description = "옵션 ID로 옵션 상세를 조회합니다")
    @GetMapping("/{optionId}")
    public ProductOptionResponse get(@PathVariable final Long optionId) {
        return ProductOptionResponse.from(productOptionService.getById(optionId));
    }

    @Operation(summary = "옵션 목록 조회", description = "페이지네이션 파라미터를 활용해 옵션 목록을 조회합니다")
    @GetMapping
    public Page<ProductOptionResponse> list(
        @RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = "10") final int size
    ) {
        return productOptionService.list(PageRequest.of(page, size))
            .map(ProductOptionResponse::from);
    }

    @Operation(summary = "옵션 수정", description = "옵션과 옵션 값을 업데이트합니다")
    @PutMapping("/{optionId}")
    public void update(@PathVariable final Long optionId, @RequestBody final OptionUpdateRequest request) {
        final var optionCommand = new UpdateProductOptionCommand(
            request.name(),
            request.additionalPrice(),
            request.type()
        );
        final var valueCommand = new UpdateOptionValueCommand(request.value());
        productOptionFacade.updateOptionWithValues(optionId, optionCommand, valueCommand);
    }

    @Operation(summary = "옵션 삭제", description = "옵션을 삭제합니다")
    @DeleteMapping("/{optionId}")
    public void delete(@PathVariable final Long optionId) {
        productOptionService.delete(optionId);
    }
}
