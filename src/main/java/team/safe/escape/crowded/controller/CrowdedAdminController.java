package team.safe.escape.crowded.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.crowded.dto.request.CreateCrowdedRequest;
import team.safe.escape.crowded.dto.response.CrowdedExitResponse;
import team.safe.escape.crowded.service.CrowdedService;

@RestController
@RequestMapping("/admin/crowded")
@RequiredArgsConstructor
public class CrowdedAdminController {

    private final CrowdedService crowdedService;

    @PostMapping
    public ApiResponse<CrowdedExitResponse> createCrowded(@Valid @RequestBody CreateCrowdedRequest request) {
        request.valid();
        CrowdedExitResponse crowdedExit = crowdedService.createCrowded(request.toCrowdedLocationDtoList(), request.toExitLocationDtoList());
        return ApiResponse.success(crowdedExit);
    }


}
