package team.safe.escape.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.config.jwt.CustomUserDetails;
import team.safe.escape.user.dto.response.MemberResponseDto;
import team.safe.escape.user.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class MemberAdminController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ApiResponse<MemberResponseDto> getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberResponseDto response = memberService.getAdminUser(userDetails.getUsername());
        return ApiResponse.success(response);
    }

}
