package team.safe.escape.crowded.entity;

import jakarta.persistence.*;
import lombok.*;
import team.safe.escape.common.entity.BaseTimeEntity;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CrowdedAreaLoc extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @JoinColumn(name = "crowded_area_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CrowdedArea crowdedArea;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public static CrowdedAreaLoc ofCreate(double latitude, double longitude, CrowdedArea crowdedArea) {
        return CrowdedAreaLoc.builder()
                .latitude(latitude)
                .longitude(longitude)
                .crowdedArea(crowdedArea)
                .build();
    }

}
