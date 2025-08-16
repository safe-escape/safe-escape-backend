package team.safe.escape.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.safe.escape.common.converter.LocationConverter;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.main.dto.response.MainAdminResponse;
import team.safe.escape.main.service.MainService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/main")
public class MainAdminController {
    private final MainService mainService;

    @GetMapping
    public ApiResponse<MainAdminResponse> getMain(@RequestParam List<Double> latitudes, @RequestParam List<Double> longitudes) {
        if (latitudes.size() != longitudes.size()) {
            throw new EscapeException(ErrorCode.INVALID_LOCATION);
        }

        double[][] locationArr = LocationConverter.toDoubleArray(latitudes, longitudes);
        MainAdminResponse response = mainService.getMainAdmin(locationArr);
        return ApiResponse.success(response);
    }


}
