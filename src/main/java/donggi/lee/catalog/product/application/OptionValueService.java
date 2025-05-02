package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.application.dto.UpdateOptionValueCommand;
import donggi.lee.catalog.product.domain.OptionValue;
import donggi.lee.catalog.product.domain.OptionValueDefinition;
import donggi.lee.catalog.product.domain.ValueSource;
import donggi.lee.catalog.product.domain.repository.OptionValueDefinitionRepository;
import donggi.lee.catalog.product.domain.repository.OptionValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionValueService {
    
    private final OptionValueRepository optionValueRepository;
    private final OptionValueDefinitionRepository definitionRepository;
    
    /**
     * 선택 타입 옵션 값 생성
     *
     * @param optionId 옵션 ID
     * @param valueName 옵션 이름
     * @return 생성된 옵션 값
     */
    @Transactional
    public OptionValue createManual(Long optionId, String valueName) {
        OptionValue value = new OptionValue(
            optionId,
            valueName,
            ValueSource.MANUAL,
            null
        );
        
        return optionValueRepository.save(value);
    }
    
    /**
     * 입력 타입 옵션 값 생성
     *
     * @param optionId 옵션 ID
     * @param definitionId 정의된 옵션 값 ID
     * @return 생성된 옵션 값
     * @throws IllegalArgumentException 옵션 값 정의를 찾을 수 없는 경우
     */
    @Transactional
    public OptionValue createPredefined(Long optionId, Long definitionId) {
        OptionValueDefinition definition = definitionRepository.findById(definitionId)
            .orElseThrow(() -> new IllegalArgumentException("옵션 값 정의를 찾을 수 없습니다: " + definitionId));
        
        OptionValue value = new OptionValue(
            optionId,
            definition.getLabel(),
            ValueSource.PREDEFINED,
            definition
        );
        
        return optionValueRepository.save(value);
    }

    /**
     * 옵션 값 수정
     *
     * @param valueId 수정할 옵션 값 ID
     * @param command 수정 요청 정보
     */
    @Transactional
    public void update(Long valueId, UpdateOptionValueCommand command) {
        // 기존 옵션 값 조회
        OptionValue value = optionValueRepository.findById(valueId)
            .orElseThrow(() -> new IllegalArgumentException("옵션 값을 찾을 수 없습니다: " + valueId));

        // 타입에 따라 업데이트
        if (command.isManual()) {
            // custom 수정 (definition 제거)
            value.updateCustomValue(command.valueName());
        } else {
            // predefined 수정 (미리 정의된 값으로 교체)
            OptionValueDefinition predefinedDefinition = definitionRepository.findById(command.definitionId())
                .orElseThrow(() -> new IllegalArgumentException("옵션 값 정의를 찾을 수 없습니다: " + command.definitionId()));
            value.updatePredefinedValue(predefinedDefinition);
        }
    }
}
