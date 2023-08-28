package eomgerm.simpleboard.global.security.handler;

import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.global.base.GlobalErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
        throw BaseException.of(GlobalErrorCode.UNAUTHORIZED);
    }
}
