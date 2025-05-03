package donggi.lee.catalog.product.application;

import donggi.lee.catalog.product.application.dto.UpdateOptionValueCommand;
import donggi.lee.catalog.product.domain.OptionValue;
import donggi.lee.catalog.product.domain.repository.OptionValueRepository;
import donggi.lee.catalog.product.exception.OptionValueNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionValueService {
    
    private final OptionValueRepository optionValueRepository;

    /**
     * 선택 타입 옵션 값 생성
     *
     * @param optionId 옵션 ID
     * @param value 옵션 값
     * @return 생성된 옵션 값
     */
    @Transactional
    public OptionValue create(Long optionId, String value) {
        OptionValue optionValue = new OptionValue(optionId, value);
        return optionValueRepository.save(optionValue);
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
            .orElseThrow(() -> new OptionValueNotFoundException(valueId));

        value.update(command.newValue());
    }
}
