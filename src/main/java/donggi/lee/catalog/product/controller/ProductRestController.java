package donggi.lee.catalog.product.controller;

import donggi.lee.catalog.product.application.ProductService;
import donggi.lee.catalog.product.controller.dto.ProductCreateRequest;
import donggi.lee.catalog.product.controller.dto.ProductResponse;
import donggi.lee.catalog.product.controller.dto.ProductUpdateRequest;
import donggi.lee.catalog.product.domain.Product;
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
@RequestMapping("/v1/products")
public class ProductRestController {

    private final ProductService productService;

    @PostMapping
    public void create(@RequestBody ProductCreateRequest request) {
        productService.create(request.name(), request.description(), request.price(), request.shippingFee());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        productService.update(id, request.name(), request.description(), request.price(), request.shippingFee());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) {
        Product product = productService.get(id);
        return ProductResponse.from(product);
    }

    @GetMapping
    public Page<ProductResponse> list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Product> productsPage = productService.list(PageRequest.of(page, size));
        return productsPage.map(ProductResponse::from);
    }
}
