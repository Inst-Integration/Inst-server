package io.github.hll2071.inst.domain.score;

import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.shared.entity.BaseEntity;
import io.github.hll2071.inst.shared.enums.Instrument;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // WARMUP은 null (관리자 등록 콘텐츠)

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScoreType scoreType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Instrument instrument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScoreStatus status; // PENDING, DONE, FAILED

    @Column(columnDefinition = "TEXT")
    private String musicXmlUrl;

    @Column(columnDefinition = "TEXT")
    private String pdfUrl;

    @Column(columnDefinition = "TEXT")
    private String youtubeUrl;

    @Builder
    private Score(User user, String title, ScoreType scoreType,
                  Instrument instrument, ScoreStatus status,
                  String youtubeUrl, String musicXmlUrl, String pdfUrl) {
        this.user = user;
        this.title = title;
        this.scoreType = scoreType;
        this.instrument = instrument;
        this.status = status;
        this.youtubeUrl = youtubeUrl;
        this.musicXmlUrl = musicXmlUrl;
        this.pdfUrl = pdfUrl;
    }

    public void markDone(String musicXmlUrl) {
        this.status = ScoreStatus.DONE;
        this.musicXmlUrl = musicXmlUrl;
    }

    public void markFailed() {
        this.status = ScoreStatus.FAILED;
    }
}