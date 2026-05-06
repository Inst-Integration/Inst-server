package io.github.hll2071.inst.domain.tmi;

import io.github.hll2071.inst.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_tmi_interests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "category"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTmiInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TmiCategory category;

    @Builder
    private UserTmiInterest(User user, TmiCategory category) {
        this.user = user;
        this.category = category;
    }
}