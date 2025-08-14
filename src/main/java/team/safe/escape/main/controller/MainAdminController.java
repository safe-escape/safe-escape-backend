package team.safe.escape.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.main.dto.request.MainRequest;
import team.safe.escape.main.dto.response.MainAdminResponse;
import team.safe.escape.main.service.MainService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/main")
public class MainAdminController {
    private final MainService mainService;

    @GetMapping
    public ApiResponse<MainAdminResponse> getMain(@RequestBody MainRequest request) {
        MainAdminResponse response = mainService.getMainAdmin(request.getLocationList());
        return ApiResponse.success(response);
    }


}
