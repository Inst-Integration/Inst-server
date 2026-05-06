package io.github.hll2071.inst.domain.tmi;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tmi_quizzes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TmiQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tmi_id", nullable = false)
    private Tmi tmi;

    @Column(nullable = false)
    private String question;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TmiQuizOption> options = new ArrayList<>();

    @Builder
    private TmiQuiz(Tmi tmi, String question) {
        this.tmi = tmi;
        this.question = question;
    }
}