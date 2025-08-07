package team.safe.escape.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.common.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String token;

    @Column
    private LocalDateTime expiresAt;

    public void expired() {
        this.expiresAt = LocalDateTime.now();
    }

}
