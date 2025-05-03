package donggi.lee.catalog.product.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import donggi.lee.catalog.product.exception.InvalidProductDescriptionException;
import donggi.lee.catalog.product.exception.InvalidProductNameException;
import donggi.lee.catalog.product.exception.InvalidProductPriceException;
import donggi.lee.catalog.product.exception.InvalidShippingFeeException;
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
            throw new InvalidProductNameException();
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidProductDescriptionException();
        }
    }

    private void validatePrice(long price) {
        if (price <= 0) {
            throw new InvalidProductPriceException();
        }
    }

    private void validateShippingFee(long shippingFee) {
        if (shippingFee < 0) {
            throw new InvalidShippingFeeException();
        }
    }
}
