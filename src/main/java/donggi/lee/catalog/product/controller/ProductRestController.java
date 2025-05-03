package donggi.lee.catalog.product.controller;

import donggi.lee.catalog.product.application.ProductService;
import donggi.lee.catalog.product.controller.dto.ProductCreateRequest;
import donggi.lee.catalog.product.controller.dto.ProductResponse;
import donggi.lee.catalog.product.controller.dto.ProductUpdateRequest;
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

@Tag(name = "상품", description = "상품 관리 API")
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "페이지네이션 파라미터를 활용해 상품 목록을 조회합니다")
    @GetMapping
    public Page<ProductResponse> list(
        @RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = "10") final int size
    ) {
        return productService.list(PageRequest.of(page, size))
            .map(ProductResponse::from);
    }

    @Operation(summary = "상품 단 건 조회", description = "ID로 상품 상세를 조회합니다")
    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable final Long id) {
        return ProductResponse.from(productService.get(id));
    }

    @Operation(summary = "상품 생성", description = "새 상품을 등록합니다")
    @PostMapping
    public void create(@RequestBody final ProductCreateRequest request) {
        productService.create(request.name(), request.description(), request.price(), request.shippingFee());
    }

    @Operation(summary = "상품 수정", description = "기존 상품을 업데이트합니다")
    @PutMapping("/{id}")
    public void update(@PathVariable final Long id, @RequestBody final ProductUpdateRequest request) {
        productService.update(id, request.name(), request.description(), request.price(), request.shippingFee());
    }

    @Operation(summary = "상품 삭제", description = "ID로 상품을 삭제합니다")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        productService.delete(id);
    }
}
