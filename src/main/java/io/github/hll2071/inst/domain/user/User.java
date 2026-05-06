package io.github.hll2071.inst.domain.user;

import io.github.hll2071.inst.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(nullable = false)
    private boolean practiceAlarm = true;

    @Column(nullable = false)
    private boolean eventAlarm = true;

    @Column(nullable = false)
    private boolean contentAlarm = true;

    @Builder
    private User(String email, String password, String nickname,
                 UserRole role, boolean emailVerified) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.emailVerified = emailVerified;
        this.practiceAlarm = true;
        this.eventAlarm = true;
        this.contentAlarm = true;
    }

    public void verifyEmail() {
        this.emailVerified = true;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAlarmSettings(boolean practiceAlarm, boolean eventAlarm, boolean contentAlarm) {
        this.practiceAlarm = practiceAlarm;
        this.eventAlarm = eventAlarm;
        this.contentAlarm = contentAlarm;
    }
}