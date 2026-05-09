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

    @Column
    private String videoUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Instrument instrument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    private Score score;

    @Column(nullable = false)
    private int completionScoreThreshold;

    @Column(nullable = false)
    private int orderIndex;

    @Builder
    private Content(String title, String videoUrl, String description, String imageUrl,
                    Instrument instrument, ContentLevel level, Score score,
                    int completionScoreThreshold, int orderIndex) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.description = description;
        this.imageUrl = imageUrl;
        this.instrument = instrument;
        this.level = level;
        this.score = score;
        this.completionScoreThreshold = completionScoreThreshold;
        this.orderIndex = orderIndex;
    }
}