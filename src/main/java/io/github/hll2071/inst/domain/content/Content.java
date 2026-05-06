package io.github.hll2071.inst.domain.content;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.shared.entity.BaseEntity;
import io.github.hll2071.inst.shared.enums.Instrument;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String videoUrl; // 강의 영상 S3 URL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Instrument instrument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id", nullable = false)
    private Score score; // 연습용 악보 (WARMUP 타입)

    @Column(nullable = false)
    private int completionScoreThreshold; // 수료 조건 점수 (예: 80)

    @Column(nullable = false)
    private int orderIndex; // 학습 순서

    @Builder
    private Content(String title, String videoUrl, Instrument instrument,
                    ContentLevel level, Score score,
                    int completionScoreThreshold, int orderIndex) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.instrument = instrument;
        this.level = level;
        this.score = score;
        this.completionScoreThreshold = completionScoreThreshold;
        this.orderIndex = orderIndex;
    }
}