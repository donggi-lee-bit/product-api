package donggi.lee.catalog.product.application.dto;

import donggi.lee.catalog.product.domain.ValueSource;

public record UpdateOptionValueCommand(
    String valueName,
    ValueSource source,
    Long definitionId
) {
    public UpdateOptionValueCommand {
        switch (source) {
            case MANUAL -> {
                // MANUAL 타입이면 valueName이 있어야 하고, definitionId는 없어야 함
                if (valueName == null || valueName.isBlank()) {
                    throw new IllegalArgumentException("MANUAL 타입 옵션 수정 시 valueName을 입력해야 합니다.");
                }
                if (definitionId != null) {
                    throw new IllegalArgumentException("MANUAL 타입 옵션 수정 시 definitionId를 사용할 수 없습니다.");
                }
            }
            case PREDEFINED -> {
                // PREDEFINED 타입이면 definitionId가 있어야 하고, valueName은 없어야 함
                if (definitionId == null) {
                    throw new IllegalArgumentException("PREDEFINED 타입 옵션 수정 시 definitionId를 입력해야 합니다.");
                }
                if (valueName != null) {
                    throw new IllegalArgumentException("PREDEFINED 타입 옵션 수정 시 valueName을 사용할 수 없습니다.");
                }
            }
            default -> throw new IllegalStateException("지원하지 않는 ValueSource 타입입니다: " + source);
        }
    }

    public boolean isManual() {
        return this.source == ValueSource.MANUAL;
    }

    public boolean isPredefined() {
        return this.source == ValueSource.PREDEFINED;
    }

    /**
     * 수동 옵션 값 수정 요청 생성자
     *
     * @param valueName 옵션 값 이름
     */
    public static UpdateOptionValueCommand manual(String valueName) {
        return new UpdateOptionValueCommand(valueName, ValueSource.MANUAL, null);
    }

    /**
     * 미리 정의된 옵션 값 수정 요청 생성자
     *
     * @param definitionId 옵션 값 정의 ID
     */
    public static UpdateOptionValueCommand predefined(Long definitionId) {
        return new UpdateOptionValueCommand(null, ValueSource.PREDEFINED, definitionId);
    }
}
