package team.safe.escape.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.common.validation.EmailValidator;
import team.safe.escape.config.jwt.CustomUserDetails;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.dto.request.LoginRequest;
import team.safe.escape.user.dto.request.RefreshRequest;
import team.safe.escape.user.dto.request.RegisterRequest;
import team.safe.escape.user.dto.response.LoginResponse;
import team.safe.escape.user.dto.response.TokenResponse;
import team.safe.escape.user.enumeration.MemberRole;
import team.safe.escape.user.service.AuthService;

@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AuthAdminController {
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (EmailValidator.isInvalidEmail(request.getEmail())) {
            throw new EscapeException(ErrorCode.INVALID_FORMAT_EMAIL, request.getEmail());
        }

        TokenResponse response = authService.register(request.getEmail(), request.getName(), request.getPassword(), MemberRole.ADMIN);
        return ApiResponse.success(response);
    }

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

    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@Valid @RequestBody RefreshRequest request,
                                              HttpServletRequest servletRequest,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        String accessToken = authService.resolveToken(servletRequest);
        Long userId = userDetails.getMemberId();
        MemberRole role = userDetails.getUserRole();
        TokenResponse response = authService.refreshToken(request.getRefreshToken(), accessToken, userId, role);
        return ApiResponse.success(response);
    }

}
