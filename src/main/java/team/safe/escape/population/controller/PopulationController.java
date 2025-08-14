package team.safe.escape.population.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.population.service.PopulationService;

@RestController
@RequestMapping("/api/populations")
@RequiredArgsConstructor
public class PopulationController {

    private final PopulationService populationService;

    @PostMapping("/load")
    public ApiResponse<Void> savePopulationArea() {
        populationService.savePopulationArea();
        return ApiResponse.success();
    }

}
