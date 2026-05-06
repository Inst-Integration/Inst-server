package io.github.hll2071.inst.domain.teacher;

import io.github.hll2071.inst.shared.entity.BaseEntity;
import io.github.hll2071.inst.shared.enums.Instrument;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Instrument instrument;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private int experienceYears;

    @Column(nullable = false)
    private double rating;

    @Builder
    private Teacher(String name, String bio, String profileImageUrl,
                    Instrument instrument, String contactEmail,
                    int experienceYears, double rating) {
        this.name = name;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.instrument = instrument;
        this.contactEmail = contactEmail;
        this.experienceYears = experienceYears;
        this.rating = rating;
    }

    public void updateRating(double rating) {
        this.rating = rating;
    }
}