package team.safe.escape.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.config.jwt.CustomUserDetails;
import team.safe.escape.user.dto.response.UserResponseDto;
import team.safe.escape.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponseDto> getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponseDto response = userService.getAdminUser(userDetails.getUsername());
        return ApiResponse.success(response);
    }

}
