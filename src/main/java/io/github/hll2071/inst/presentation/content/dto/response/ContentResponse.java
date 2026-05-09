package io.github.hll2071.inst.presentation.content.dto.response;

import io.github.hll2071.inst.domain.content.Content;
import io.github.hll2071.inst.domain.content.ContentLevel;
import io.github.hll2071.inst.shared.enums.Instrument;

public record ContentResponse(
        Long id,
        String title,
        String videoUrl,
        String description,
        String imageUrl,
        Instrument instrument,
        ContentLevel level,
        Long scoreId,
        String scoreTitle,
        String musicXmlUrl,
        int completionScoreThreshold,
        int orderIndex,
        boolean completed
) {
    public static ContentResponse from(Content content, boolean completed) {
        return new ContentResponse(
                content.getId(),
                content.getTitle(),
                content.getVideoUrl(),
                content.getDescription(),
                content.getImageUrl(),
                content.getInstrument(),
                content.getLevel(),
                content.getScore() != null ? content.getScore().getId() : null,
                content.getScore() != null ? content.getScore().getTitle() : null,
                content.getScore() != null ? content.getScore().getMusicXmlUrl() : null,
                content.getCompletionScoreThreshold(),
                content.getOrderIndex(),
                completed
        );
    }
}