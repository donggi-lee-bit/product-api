package donggi.lee.catalog.product.controller;

import donggi.lee.catalog.product.application.ProductOptionFacade;
import donggi.lee.catalog.product.application.ProductOptionService;
import donggi.lee.catalog.product.application.dto.CreateOptionWithValuesCommand;
import donggi.lee.catalog.product.application.dto.UpdateOptionValueCommand;
import donggi.lee.catalog.product.application.dto.UpdateProductOptionCommand;
import donggi.lee.catalog.product.controller.dto.OptionCreateRequest;
import donggi.lee.catalog.product.controller.dto.ProductOptionResponse;
import donggi.lee.catalog.product.controller.dto.OptionUpdateRequest;
import donggi.lee.catalog.product.domain.ProductOption;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product-options")
public class ProductOptionRestController {

    private final ProductOptionService productOptionService;
    private final ProductOptionFacade productOptionFacade;

    @PostMapping
    public ProductOptionResponse create(@RequestBody OptionCreateRequest request) {
        CreateOptionWithValuesCommand command = new CreateOptionWithValuesCommand(
            request.productId(),
            request.name(),
            request.additionalPrice(),
            request.type(),
            request.value()
        );

        ProductOption createdOption = productOptionFacade.createOptionWithValues(command);
        return ProductOptionResponse.from(createdOption);
    }

    @GetMapping("/{id}")
    public ProductOptionResponse get(@PathVariable Long id) {
        ProductOption option = productOptionService.getById(id);
        return ProductOptionResponse.from(option);
    }

    @GetMapping
    public Page<ProductOptionResponse> list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductOption> productOptionsPage = productOptionService.list(PageRequest.of(page, size));
        return productOptionsPage.map(ProductOptionResponse::from);
    }

    @PutMapping("/{optionId}")
    public void update(
        @PathVariable Long optionId,
        @RequestBody OptionUpdateRequest request
    ) {
        UpdateProductOptionCommand optionCommand = new UpdateProductOptionCommand(request.name(), request.additionalPrice(), request.type());

        UpdateOptionValueCommand valueCommand = new UpdateOptionValueCommand(request.value());

        productOptionFacade.updateOptionWithValues(optionId, optionCommand, valueCommand);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productOptionService.delete(id);
    }
}
