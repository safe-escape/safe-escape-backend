package team.safe.escape.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.user.dto.response.MemberResponseDto;
import team.safe.escape.user.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ApiResponse<MemberResponseDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        MemberResponseDto response = memberService.getUser(userDetails.getUsername());
        return ApiResponse.success(response);
    }

}
