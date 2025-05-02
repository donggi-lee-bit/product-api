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
    private String valueName;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", length = 20, nullable = false)
    private ValueSource source;

    /**
     * 미리 정의된 옵션 값 정의 (PREDEFINED 소스인 경우에만 사용)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "definition_id")
    private OptionValueDefinition definition;

    protected OptionValue() {}

    public OptionValue(Long optionId, String valueName, ValueSource source, OptionValueDefinition definition) {
        this.optionId = optionId;
        this.valueName = valueName;
        this.source = source;
        this.definition = definition;

        validateState();
    }

    public void updateCustomValue(String custom) {
        this.valueName  = custom;
        this.source     = ValueSource.MANUAL;
        this.definition = null;

        validateState();
    }

    public void updatePredefinedValue(OptionValueDefinition definition) {
        this.valueName  = definition.getLabel();
        this.source     = ValueSource.PREDEFINED;
        this.definition = definition;

        validateState();
    }
    
    /**
     * 엔티티 상태 유효성 검증
     * MANUAL 경우 definition이 null이어야 함
     * PREDEFINED 경우 definition이 null이 아니어야 함
     */
    private void validateState() {
        if (source == ValueSource.MANUAL && definition != null) {
            throw new IllegalStateException("MANUAL 소스 옵션 값은 정의 참조를 가질 수 없습니다");
        }
        
        if (source == ValueSource.PREDEFINED && definition == null) {
            throw new IllegalStateException("PREDEFINED 소스 옵션 값은 유효한 정의 참조가 필요합니다");
        }
    }
}
