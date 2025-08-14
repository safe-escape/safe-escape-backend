package team.safe.escape.crowded.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import team.safe.escape.exit.entity.EmergencyExit;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CrowdedAreaExit {

    @EmbeddedId
    private CrowdedAreaExitId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("crowdedAreaId")
    @JoinColumn(name = "crowded_area_id")
    private CrowdedArea crowdedArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exitId")
    @JoinColumn(name = "exit_id")
    private EmergencyExit exit;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static CrowdedAreaExit ofCreate(CrowdedArea crowdedArea, EmergencyExit exit) {
        return CrowdedAreaExit.builder()
                .id(CrowdedAreaExitId.of(crowdedArea.getId(), exit.getId()))
                .crowdedArea(crowdedArea)
                .exit(exit)
                .build();
    }
}
