package team.safe.escape.shelter.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterBookmark {

    @EmbeddedId
    private ShelterBookmarkId id;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
