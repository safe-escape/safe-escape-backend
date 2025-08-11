package team.safe.escape.shelter.entity;

import jakarta.persistence.*;
import lombok.*;
import team.safe.escape.common.entity.BaseTimeEntity;
import team.safe.escape.shelter.dto.ShelterData;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Shelter extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public static Shelter ofCreateByShelterData(ShelterData data) {
        return Shelter.builder()
                .name(data.getFacilityName())
                .address(data.getAddress())
                .latitude(data.getLatitude())
                .longitude(data.getLongitude())
                .build();
    }

}
