package donggi.lee.catalog.product.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 옵션 값 엔티티
 * 상품 옵션에 속한 개별 값 정보를 저장
 */
@Getter
@Entity
@Table(name = "option_value")
public class OptionValue extends BaseEntity {

    @Column(name = "option_id", nullable = false)
    private Long optionId;

    @Column(name = "value_name", nullable = false)
    private String value;

    protected OptionValue() {}

    public OptionValue(Long optionId, String value) {
        this.optionId = optionId;
        this.value = value;
    }

    public void update(String newValue) {
        this.value = newValue;
    }
}
