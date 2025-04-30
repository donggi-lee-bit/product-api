package donggi.lee.catalog.product.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "option_value")
public class OptionValue extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption option;

    @Column(name = "value_name", nullable = false)
    private String name;

    protected OptionValue() {}

    public OptionValue(ProductOption option, String name) {
        this.option = option;
        this.name = name;
    }
}
