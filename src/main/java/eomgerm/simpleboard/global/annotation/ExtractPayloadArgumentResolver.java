package eomgerm.simpleboard.global.annotation;

import eomgerm.simpleboard.auth.exception.AuthErrorCode;
import eomgerm.simpleboard.auth.utils.AuthorizationExtractor;
import eomgerm.simpleboard.auth.utils.JwtTokenProvider;
import eomgerm.simpleboard.global.base.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class ExtractPayloadArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ExtractPayload.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = AuthorizationExtractor.extractToken(request)
                                             .orElseThrow(() -> BaseException.of(AuthErrorCode.AUTH_INVALID_TOKEN));
        validateToken(token);
        return jwtTokenProvider.getId(token);
    }

    private void validateToken(String token) {
        jwtTokenProvider.isTokenValid(token);
    }
}
