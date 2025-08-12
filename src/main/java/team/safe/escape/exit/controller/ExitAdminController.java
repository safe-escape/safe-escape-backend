package team.safe.escape.exit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.exit.service.ExitService;
import team.safe.escape.shelter.dto.response.ShelterResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/exits")
public class ExitAdminController {

    private final ExitService exitService;

    @GetMapping("/{exitId}/shelter/nearby")
    public ApiResponse<ShelterResponse> getNearByShelter(@PathVariable Long exitId) {
        ShelterResponse nearByShelter = exitService.getNearByShelter(exitId);
        return ApiResponse.success(nearByShelter);
    }

}
