package donggi.lee.catalog.product.domain.repository;

import donggi.lee.catalog.product.domain.OptionValue;

import java.util.List;
import java.util.Optional;

public interface OptionValueRepository {
    
    /**
     * 옵션 값 저장
     * 
     * @param optionValue 저장할 옵션 값
     * @return 저장된 옵션 값
     */
    OptionValue save(OptionValue optionValue);
    
    /**
     * ID로 옵션 값 조회
     * 
     * @param id 옵션 값 ID
     * @return 옵션 값 Optional
     */
    Optional<OptionValue> findById(Long id);
    
    /**
     * 옵션 ID로 옵션 값 목록 조회
     * 
     * @param optionId 옵션 ID
     * @return 옵션 값 목록
     */
    List<OptionValue> findByOptionId(Long optionId);
    
    /**
     * 옵션 값 삭제
     * 
     * @param optionValue 삭제할 옵션 값
     */
    void delete(OptionValue optionValue);
    
    /**
     * 옵션 ID로 옵션 값 모두 삭제
     * 
     * @param optionId 옵션 ID
     */
    void deleteAllByOptionId(Long optionId);
}
