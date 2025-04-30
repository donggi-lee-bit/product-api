package donggi.lee.catalog.product.controller.dto;

import donggi.lee.catalog.product.domain.Product;

public record ProductResponse(
    Long id,
    String name,
    String description,
    Long price,
    Long shippingFee
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getShippingFee()
        );
    }
}
