package team.safe.escape.population.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PopulationAreaData {
    private double latitude;
    private double longitude;
    private String categoryName;
    private String areaName;
    private String areaCode;
}
