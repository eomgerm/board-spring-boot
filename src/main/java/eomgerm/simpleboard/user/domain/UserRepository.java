package eomgerm.simpleboard.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.refreshToken from User u WHERE u.userId = :userId")
    Optional<String> findRefreshTokenByUserId(@Param("userId") Long userId);

}
