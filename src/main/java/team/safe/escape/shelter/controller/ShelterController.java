package team.safe.escape.shelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    @DeleteMapping("/{shelterId}/bookmark")
    public ApiResponse<Void> deleteBookmark(@PathVariable Long shelterId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        shelterService.deleteBookmark(userDetails.getMemberId(), shelterId);
        return ApiResponse.success();
    }

    @PostMapping("/load")
    public ApiResponse<Void> saveShelter() {
        shelterService.saveShelter();
        return ApiResponse.success();
    }

}
