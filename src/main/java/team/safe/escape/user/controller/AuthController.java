package team.safe.escape.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.user.dto.request.RegisterRequest;
import team.safe.escape.user.dto.response.RegisterResponse;
import team.safe.escape.user.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request.getEmail(), request.getName(), request.getPassword());
        return ApiResponse.success(response);
    }

}
