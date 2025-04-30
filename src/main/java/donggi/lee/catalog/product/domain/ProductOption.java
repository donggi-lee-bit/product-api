package donggi.lee.catalog.product.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "product_option")
public class ProductOption extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "option_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "option_type", length = 20, nullable = false)
    private OptionType type;

    @Column(name = "additional_price", nullable = false)
    private Long additionalPrice;

    protected ProductOption() {}

    public ProductOption(Product product, String name, OptionType type, Long additionalPrice) {
        this.product = product;
        this.name = name;
        this.type = type;
        this.additionalPrice = additionalPrice;
    }
}
