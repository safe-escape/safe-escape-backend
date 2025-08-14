package team.safe.escape.population.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PopulationApiResponse {
    @JsonProperty("SeoulRtd.citydata_ppltn")
    private List<CityData> cityData;
}


