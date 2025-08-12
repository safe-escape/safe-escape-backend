package team.safe.escape.crowded.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CrowdedAreaExitId implements Serializable {
    private long crowdedAreaId;
    private long exitId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrowdedAreaExitId that = (CrowdedAreaExitId) o;
        return crowdedAreaId == that.crowdedAreaId && exitId == that.exitId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(crowdedAreaId, exitId);
    }
}
