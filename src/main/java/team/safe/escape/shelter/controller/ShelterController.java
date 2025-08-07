package team.safe.escape.shelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.config.jwt.CustomUserDetails;
import team.safe.escape.shelter.dto.response.ShelterResponse;
import team.safe.escape.shelter.service.ShelterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shelters")
public class ShelterController {

    private final ShelterService shelterService;

    @PostMapping("/{shelterId}/bookmark")
    public ApiResponse<ShelterResponse> bookmarkShelter(@PathVariable Long shelterId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ShelterResponse response = shelterService.bookmarkShelter(userDetails.getMemberId(), shelterId);
        return ApiResponse.success(response);
    }
}
