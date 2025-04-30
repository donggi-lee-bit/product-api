package donggi.lee.catalog.product.controller.dto;

public record ProductUpdateRequest(
    String name,
    String description,
    Long price,
    Long shippingFee
) { }
