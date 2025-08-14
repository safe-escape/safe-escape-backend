package team.safe.escape.population.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.population.entity.PopulationLevel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopulationDto {
    private double latitude;
    private double longitude;
    private PopulationLevel level;
}
