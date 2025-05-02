package donggi.lee.catalog.product.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 상품 옵션 엔티티
 * 상품에 추가할 수 있는 옵션 정보를 저장
 */
@Getter
@Entity
@Table(name = "product_option")
public class ProductOption extends BaseEntity {
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "option_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "option_type", length = 20, nullable = false)
    private OptionType type;

    @Column(name = "additional_price", nullable = false)
    private Long additionalPrice;

    protected ProductOption() {}

    public ProductOption(Long productId, String name, OptionType type, Long additionalPrice) {
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.additionalPrice = additionalPrice;
    }

    public ProductOption update(String name, Long additionalPrice, OptionType type) {
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.type = type;
        return this;
    }
}
