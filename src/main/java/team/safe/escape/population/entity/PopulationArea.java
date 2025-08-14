package team.safe.escape.population.entity;

import jakarta.persistence.*;
import lombok.*;
import team.safe.escape.common.entity.BaseTimeEntity;
import team.safe.escape.population.dto.PopulationAreaData;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PopulationArea extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String categoryName;

    @Column
    private String areaName;

    @Column
    private String areaCode;

    @Column
    private double latitude;

    @Column
    private double longitude;

    public static PopulationArea ofCreateByDate(PopulationAreaData populationAreaData) {
        return PopulationArea.builder()
                .categoryName(populationAreaData.getCategoryName())
                .areaCode(populationAreaData.getAreaCode())
                .areaName(populationAreaData.getAreaName())
                .latitude(populationAreaData.getLatitude())
                .longitude(populationAreaData.getLongitude())
                .build();
    }

}
