package team.safe.escape.shelter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import team.safe.escape.common.entity.BaseTimeEntity;

@Entity
@Table
@Getter
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

}
