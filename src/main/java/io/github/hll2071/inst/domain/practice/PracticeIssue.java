package io.github.hll2071.inst.domain.practice;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "practice_issues")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PracticeIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practice_id", nullable = false)
    private Practice practice;

    @Column(nullable = false)
    private int timestampSeconds; // 타임라인 위치

    @Column(nullable = false)
    private String message; // "박자가 +80ms 늦음 · 손목 자세 주의"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueCategory category; // PITCH, RHYTHM, POSTURE

    @Builder
    private PracticeIssue(Practice practice, int timestampSeconds,
                          String message, IssueCategory category) {
        this.practice = practice;
        this.timestampSeconds = timestampSeconds;
        this.message = message;
        this.category = category;
    }
}