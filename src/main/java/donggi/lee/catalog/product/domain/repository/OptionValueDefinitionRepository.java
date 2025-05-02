package donggi.lee.catalog.product.domain.repository;

import donggi.lee.catalog.product.domain.OptionValueDefinition;

import java.util.List;
import java.util.Optional;

public interface OptionValueDefinitionRepository {
    
    /**
     * 옵션 값 정의 저장
     * 
     * @param definition 저장할 옵션 값 정의
     * @return 저장된 옵션 값 정의
     */
    OptionValueDefinition save(OptionValueDefinition definition);
    
    /**
     * ID로 옵션 값 정의 조회
     * 
     * @param id 옵션 값 정의 ID
     * @return 옵션 값 정의 Optional
     */
    Optional<OptionValueDefinition> findById(Long id);
    
    /**
     * 코드로 옵션 값 정의 조회
     * 
     * @param code 옵션 값 정의 코드
     * @return 옵션 값 정의 Optional
     */
    Optional<OptionValueDefinition> findByCode(String code);
    
    /**
     * 모든 옵션 값 정의 조회
     * 
     * @return 옵션 값 정의 목록
     */
    List<OptionValueDefinition> findAll();
    
    /**
     * 옵션 값 정의 삭제
     * 
     * @param definition 삭제할 옵션 값 정의
     */
    void delete(OptionValueDefinition definition);
}
