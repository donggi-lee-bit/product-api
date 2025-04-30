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

    public Product(String name, String description, long price, long shippingFee) {
        validateName(name);
        validateDescription(description);
        validatePrice(price);
        validateShippingFee(shippingFee);

        this.name = name;
        this.description = description;
        this.price = price;
        this.shippingFee = shippingFee;
    }

    public void changeDetails(String name, String description, long price, long shippingFee) {
        validateName(name);
        validateDescription(description);
        validatePrice(price);
        validateShippingFee(shippingFee);

        this.name = name;
        this.description = description;
        this.price = price;
        this.shippingFee = shippingFee;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("상품 상세 설명은 필수입니다.");
        }
    }

    private void validatePrice(long price) {
        if (price <= 0) {
            throw new IllegalArgumentException("상품 가격은 0보다 커야 합니다.");
        }
    }

    private void validateShippingFee(long shippingFee) {
        if (shippingFee < 0) {
            throw new IllegalArgumentException("배송비는 0 이상이어야 합니다.");
        }
    }
}
