package donggi.lee.catalog.product.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 옵션 값 정의 엔티티
 * 미리 정의된 옵션 값의 정보를 저장
 */
@Getter
@Entity
@Table(name = "option_value_definition")
public class OptionValueDefinition extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    protected OptionValueDefinition() {}

    public OptionValueDefinition(String code, String label, Integer displayOrder) {
        this.code = code;
        this.label = label;
        this.displayOrder = displayOrder;
    }
}
