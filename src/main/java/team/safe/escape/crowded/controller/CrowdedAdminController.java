package team.safe.escape.crowded.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.crowded.dto.request.CreateCrowdedRequest;
import team.safe.escape.crowded.dto.response.CrowdedExitResponse;
import team.safe.escape.crowded.service.CrowdedService;

@RestController
@RequestMapping("/admin/crowded-area")
@RequiredArgsConstructor
public class CrowdedAdminController {

    private final CrowdedService crowdedService;

    @PostMapping
    public ApiResponse<CrowdedExitResponse> createCrowdedArea(@Valid @RequestBody CreateCrowdedRequest request) {
        request.valid();
        CrowdedExitResponse crowdedExit = crowdedService.createCrowdedArea(request.toCrowdedLocationDtoList(), request.toExitLocationDtoList());
        return ApiResponse.success(crowdedExit);
    }

    @DeleteMapping("/{crowdedAreaId}")
    public ApiResponse<Long> deleteCrowdedArea(@PathVariable Long crowdedAreaId) {
        return ApiResponse.success(crowdedService.deleteCrowdedArea(crowdedAreaId));
    }


}
