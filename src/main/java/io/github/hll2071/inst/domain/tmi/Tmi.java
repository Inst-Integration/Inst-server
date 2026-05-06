package io.github.hll2071.inst.domain.tmi;

import io.github.hll2071.inst.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tmis")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tmi extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TmiCategory category;

    @OneToMany(mappedBy = "tmi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TmiQuiz> quizzes = new ArrayList<>();

    @Builder
    private Tmi(String title, String content, TmiCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}