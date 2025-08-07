package team.safe.escape.shelter.entity;

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
public class ShelterBookmarkId implements Serializable {

    private Long memberId;
    private Long shelterId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterBookmarkId that = (ShelterBookmarkId) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(shelterId, that.shelterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, shelterId);
    }
}
