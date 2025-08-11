package team.safe.escape.shelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.config.jwt.CustomUserDetails;
import team.safe.escape.shelter.dto.response.ShelterResponse;
import team.safe.escape.shelter.service.ShelterBookmarkService;

import java.util.List;

@RestController
@RequestMapping("/api/shelters/bookmark")
@RequiredArgsConstructor
public class ShelterBookmarkController {

    private final ShelterBookmarkService shelterBookmarkService;

    @GetMapping
    public ApiResponse<List<ShelterResponse>> getBookmarkShelters(@AuthenticationPrincipal CustomUserDetails userDetails) {
        final Long memberId = userDetails.getMemberId();
        List<ShelterResponse> bookmarkShelters = shelterBookmarkService.getBookmarkShelters(memberId);
        return ApiResponse.success(bookmarkShelters);
    }


}
