package team.safe.escape.crowded.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CrowdedArea {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "crowdedArea", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CrowdedAreaExit> crowdedAreaExitList;

    @OneToMany(mappedBy = "crowdedArea", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CrowdedAreaLoc> crowdedAreaLocList;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static CrowdedArea ofCreate() {
        return new CrowdedArea();
    }
}
