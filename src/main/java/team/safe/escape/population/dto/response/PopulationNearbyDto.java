package team.safe.escape.population.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.population.entity.PopulationLevel;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopulationNearbyDto {
    private String name;
    private double latitude;
    private double longitude;
    private PopulationLevel level;
}
