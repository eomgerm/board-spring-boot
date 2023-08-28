package eomgerm.simpleboard.user.domain;

import eomgerm.simpleboard.global.base.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;

    @Embedded
    private Password password;

    private String username;

    private String refreshToken;
    
    private String profileImage;

    private User(String email, Password password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static User createUser(String email, Password password, String username) {
        return new User(email, password, username);
    }
}
