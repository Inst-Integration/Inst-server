package io.github.hll2071.inst.domain.effect;

import io.github.hll2071.inst.shared.entity.BaseEntity;
import io.github.hll2071.inst.shared.enums.Instrument;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "effects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Effect extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Instrument instrument;

    @Column(nullable = false)
    private String previewAudioUrl; // 미리듣기 S3 URL

    @Column(nullable = false)
    private String brandName; // 이펙터 제조사

    @Builder
    private Effect(String name, String description, Instrument instrument,
                   String previewAudioUrl, String brandName) {
        this.name = name;
        this.description = description;
        this.instrument = instrument;
        this.previewAudioUrl = previewAudioUrl;
        this.brandName = brandName;
    }
}