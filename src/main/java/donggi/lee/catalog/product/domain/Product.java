package donggi.lee.catalog.product.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long shippingFee;

    protected Product() {}

    public Product(String name, String description, Long price, Long shippingFee) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.shippingFee = shippingFee;
    }
}
