package team.safe.escape.population.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForecastInfo {
    private LocalDateTime dateTime;     // FCST_TIME
    private String areaName;     // AREA_NM
    private String areaCode;     // AREA_CD
    private String predictLevel; // FCST_CONGEST_LVL
}