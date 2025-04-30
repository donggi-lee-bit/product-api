package donggi.lee.catalog.user.domain;

import donggi.lee.catalog.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String encodedPassword;

    protected User() {}

    public User(String email, String encodedPassword) {
        this.email = email;
        this.encodedPassword = encodedPassword;
    }
}
