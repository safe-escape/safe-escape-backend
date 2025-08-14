package team.safe.escape.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.config.jwt.CustomUserDetails;
import team.safe.escape.main.dto.request.MainRequest;
import team.safe.escape.main.dto.response.MainApiResponse;
import team.safe.escape.main.service.MainService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainApiController {

    private final MainService mainService;

    @GetMapping
    public ApiResponse<MainApiResponse> getMain(@RequestBody MainRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = Optional.ofNullable(userDetails).map(CustomUserDetails::getMemberId).orElse(null);
        MainApiResponse response = mainService.getMainApi(request.getLocationList(), memberId);
        return ApiResponse.success(response);
    }
}
