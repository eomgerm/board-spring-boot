package eomgerm.simpleboard.auth.utils;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuthorizationExtractor {

    private static final String BEARER = "Bearer";

    public static Optional<String> extractToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (token.isEmpty()) {
            return Optional.empty();
        }
        return checkToken(token.split(" "));
    }

    private static Optional<String> checkToken(String[] parts) {
        if (parts.length == 2 && parts[0].equals(BEARER)) {
            return Optional.ofNullable(parts[1]);
        }
        return Optional.empty();
    }

}
