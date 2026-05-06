package io.github.hll2071.inst.presentation.practice.dto.request;

import io.github.hll2071.inst.application.practice.SavePracticeResultUseCase;
import io.github.hll2071.inst.domain.practice.IssueCategory;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SavePracticeResultRequest(
        Long scoreId,
        Long contentId,
        @NotNull int pitchScore,
        @NotNull int rhythmScore,
        @NotNull int postureScore,
        @NotNull int totalScore,
        @NotNull int durationSeconds,
        List<IssueRequest> issues
) {
    public record IssueRequest(
            int timestampSeconds,
            String message,
            IssueCategory category
    ) {}

    public List<SavePracticeResultUseCase.IssueCommand> toIssueCommands() {
        if (issues == null) return List.of();
        return issues.stream()
                .map(i -> new SavePracticeResultUseCase.IssueCommand(
                        i.timestampSeconds(), i.message(), i.category()))
                .toList();
    }
}