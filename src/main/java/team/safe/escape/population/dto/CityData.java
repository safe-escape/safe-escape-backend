package team.safe.escape.population.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CityData {
    @JsonProperty("AREA_NM")
    private String areaNm;

    @JsonProperty("AREA_CD")
    private String areaCd;

    @JsonProperty("FCST_PPLTN")
    private List<ForecastData> forecasts;
}
