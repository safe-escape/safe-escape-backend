package team.safe.escape.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.common.validation.EmailValidator;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.dto.request.LoginRequest;
import team.safe.escape.user.dto.response.LoginResponse;
import team.safe.escape.user.service.AuthService;

@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AuthAdminController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        if (EmailValidator.isInvalidEmail(request.getEmail())) {
            throw new EscapeException(ErrorCode.INVALID_FORMAT_EMAIL, request.getEmail());
        }

        LoginResponse response = authService.loginByAdmin(request.getEmail(), request.getPassword());
        return ApiResponse.success(response);
    }

    @PostMapping("/logout")
    public ApiResponse<Object> logout(HttpServletRequest request) {
        String token = authService.resolveToken(request);
        authService.logout(token);
        return ApiResponse.success();
    }


}
