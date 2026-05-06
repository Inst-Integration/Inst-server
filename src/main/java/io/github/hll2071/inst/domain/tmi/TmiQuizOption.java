package io.github.hll2071.inst.domain.tmi;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tmi_quiz_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TmiQuizOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private TmiQuiz quiz;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean correct;

    @Builder
    private TmiQuizOption(TmiQuiz quiz, String content, boolean correct) {
        this.quiz = quiz;
        this.content = content;
        this.correct = correct;
    }
}