package donggi.lee.catalog.product.application;

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
}
