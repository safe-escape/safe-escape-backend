package team.safe.escape.user.controller;

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
import team.safe.escape.user.dto.request.RegisterRequest;
import team.safe.escape.user.dto.response.LoginResponse;
import team.safe.escape.user.dto.response.TokenResponse;
import team.safe.escape.user.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (EmailValidator.isInvalidEmail(request.getEmail())) {
            throw new EscapeException(ErrorCode.INVALID_FORMAT_EMAIL, request.getEmail());
        }

        TokenResponse response = authService.register(request.getEmail(), request.getName(), request.getPassword());
        return ApiResponse.success(response);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        if (EmailValidator.isInvalidEmail(request.getEmail())) {
            throw new EscapeException(ErrorCode.INVALID_FORMAT_EMAIL, request.getEmail());
        }

        LoginResponse response = authService.loginByUser(request.getEmail(), request.getPassword());
        return ApiResponse.success(response);
    }

}
