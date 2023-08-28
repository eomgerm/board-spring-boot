package eomgerm.simpleboard.auth.controller;

import eomgerm.simpleboard.auth.controller.dto.request.LoginRequest;
import eomgerm.simpleboard.auth.controller.dto.response.LoginResponse;
import eomgerm.simpleboard.auth.service.AuthService;
import eomgerm.simpleboard.global.base.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return new BaseResponse<>(authService.login(request.email(), request.password()));
    }
}
