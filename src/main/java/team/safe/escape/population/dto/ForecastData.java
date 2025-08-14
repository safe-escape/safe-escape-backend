package team.safe.escape.population.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import team.safe.escape.common.util.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ForecastData {
    @JsonProperty("FCST_TIME")
    private String fcstTime;

    @JsonProperty("FCST_CONGEST_LVL")
    private String fcstCongestLvl;

    public boolean isValidForToday() {
        LocalDateTime dateTime = LocalDateTime.parse(this.fcstTime, DateTimeUtils.YYYY_MM_DD_HH_MM);
        return dateTime.toLocalDate().equals(LocalDate.now());
    }
}