package team.safe.escape.crowded.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import team.safe.escape.shelter.entity.ShelterBookmarkId;

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

    @CreationTimestamp
    private LocalDateTime createdAt;
}
