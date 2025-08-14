package team.safe.escape.population.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.safe.escape.common.response.ApiResponse;
import team.safe.escape.population.dto.response.PopulationNearbyDto;
import team.safe.escape.population.entity.Population;
import team.safe.escape.population.service.PopulationService;

import java.util.List;

@RestController
@RequestMapping("/api/populations")
@RequiredArgsConstructor
public class PopulationController {

    private final PopulationService populationService;

    @GetMapping("/nearby")
    public ApiResponse<List<PopulationNearbyDto>> getPopulationNearby(@RequestParam double latitude, @RequestParam double longitude, @RequestParam(defaultValue = "5") int size) {
        List<PopulationNearbyDto> response = populationService.getPopulationNearby(latitude, longitude, size);
        return ApiResponse.success(response);
    }

    @PostMapping("/area/save")
    public ApiResponse<Void> savePopulationArea() {
        populationService.savePopulationArea();
        return ApiResponse.success();
    }

    @PostMapping("/save")
    public List<Population> getForecasts() {
        return populationService.savePopulation();
    }

}
