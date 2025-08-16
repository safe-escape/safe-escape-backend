package team.safe.escape.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.converter.LocationConverter;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.config.jwt.CustomUserDetails;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.main.dto.response.MainApiResponse;
import team.safe.escape.main.service.MainService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainApiController {

    private final MainService mainService;

    @GetMapping
    public ApiResponse<MainApiResponse> getMain(
            @RequestParam List<Double> latitudes,
            @RequestParam List<Double> longitudes,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (latitudes.size() != longitudes.size()) {
            throw new EscapeException(ErrorCode.INVALID_LOCATION);
        }

        double[][] locationArr = LocationConverter.toDoubleArray(latitudes, longitudes);
        Long memberId = Optional.ofNullable(userDetails).map(CustomUserDetails::getMemberId).orElse(null);
        MainApiResponse response = mainService.getMainApi(locationArr, memberId);
        return ApiResponse.success(response);
    }
}
