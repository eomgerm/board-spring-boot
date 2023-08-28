package eomgerm.simpleboard.user.controller;

import eomgerm.simpleboard.global.base.BaseResponse;
import eomgerm.simpleboard.global.base.GlobalErrorCode;
import eomgerm.simpleboard.user.controller.dto.request.SignUpRequest;
import eomgerm.simpleboard.user.controller.dto.response.SignUpResponse;
import eomgerm.simpleboard.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
@Tag(name = "user", description = "the user API")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest request) {
        Long userId = userService.saveUser(request.toUser());

        return new ResponseEntity<BaseResponse<SignUpResponse>>(
            new BaseResponse<>(
                GlobalErrorCode.SUCCESS_CREATED,
                SignUpResponse
                    .builder()
                    .userId(userId)
                    .build()
            ),
            HttpStatus.CREATED);
    }

}
