package donggi.lee.catalog.product.controller.dto;

public record ProductCreateRequest(
    String name,
    String description,
    Long price,
    Long shippingFee
) { }
