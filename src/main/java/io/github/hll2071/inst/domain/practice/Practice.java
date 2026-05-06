package io.github.hll2071.inst.domain.practice;

import io.github.hll2071.inst.domain.content.Content;
import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "practices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Practice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    private Score score; // 일반 연주 시 사용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content; // 학습 콘텐츠 연주 시 사용

    @Column(nullable = false)
    private int pitchScore;

    @Column(nullable = false)
    private int rhythmScore;

    @Column(nullable = false)
    private int postureScore;

    @Column(nullable = false)
    private int totalScore; // 종합 정확도 점수

    @Column(nullable = false)
    private int durationSeconds;

    @OneToMany(mappedBy = "practice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PracticeIssue> issues = new ArrayList<>();

    @Builder
    private Practice(User user, Score score, Content content,
                     int pitchScore, int rhythmScore, int postureScore,
                     int totalScore, int durationSeconds) {
        this.user = user;
        this.score = score;
        this.content = content;
        this.pitchScore = pitchScore;
        this.rhythmScore = rhythmScore;
        this.postureScore = postureScore;
        this.totalScore = totalScore;
        this.durationSeconds = durationSeconds;
    }

    public void addIssue(PracticeIssue issue) {
        this.issues.add(issue);
    }
}