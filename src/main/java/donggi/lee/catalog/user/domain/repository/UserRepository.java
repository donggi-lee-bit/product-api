package donggi.lee.catalog.user.domain.repository;

import donggi.lee.catalog.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    /**
     * 주어진 사용자 엔티티를 저장합니다.
     *
     * @param user 저장할 사용자 엔티티
     * @return 저장된 사용자 엔티티
     */
    User save(final User user);

    /**
     * 이메일을 기준으로 사용자를 조회합니다.
     *
     * @param email 조회할 사용자 이메일
     * @return 조회된 사용자를 Optional에 담아 반환, 없으면 빈 Optional
     */
    Optional<User> findByEmail(final String email);

    /**
     * 이메일 중복 여부를 확인합니다.
     *
     * @param email 중복 확인할 이메일
     * @return 이미 등록된 이메일이면 true, 그렇지 않으면 false
     */
    boolean existsByEmail(final String email);
}
