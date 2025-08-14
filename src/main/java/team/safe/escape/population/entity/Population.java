package team.safe.escape.population.entity;

import jakarta.persistence.*;
import lombok.*;
import team.safe.escape.common.entity.BaseTimeEntity;
import team.safe.escape.common.util.DateTimeUtils;
import team.safe.escape.population.dto.CityData;
import team.safe.escape.population.dto.ForecastData;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Population extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private LocalDateTime dateTime;

    @Column
    private String areaName;

    @Column
    private String areaCode;

    @Column
    private String level;

    public static Population ofCreateByApiResponse(ForecastData forecastData, CityData cityData) {
        return Population.builder()
                .dateTime(LocalDateTime.parse(forecastData.getFcstTime(), DateTimeUtils.YYYY_MM_DD_HH_MM))
                .areaName(cityData.getAreaNm())
                .areaCode(cityData.getAreaCd())
                .level(forecastData.getFcstCongestLvl())
                .build();
    }

}
