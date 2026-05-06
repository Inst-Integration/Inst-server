package io.github.hll2071.inst.presentation.practice.dto.response;

import io.github.hll2071.inst.domain.practice.IssueCategory;
import io.github.hll2071.inst.domain.practice.Practice;
import io.github.hll2071.inst.domain.practice.PracticeIssue;

import java.time.LocalDateTime;
import java.util.List;

public record PracticeResponse(
        Long id,
        Long scoreId,
        Long contentId,
        String scoreTitle,
        int pitchScore,
        int rhythmScore,
        int postureScore,
        int totalScore,
        int durationSeconds,
        List<IssueResponse> issues,
        LocalDateTime createdAt
) {
    public record IssueResponse(
            int timestampSeconds,
            String message,
            IssueCategory category
    ) {
        public static IssueResponse from(PracticeIssue issue) {
            return new IssueResponse(
                    issue.getTimestampSeconds(),
                    issue.getMessage(),
                    issue.getCategory()
            );
        }
    }

    public static PracticeResponse from(Practice practice) {
        String title = practice.getScore() != null
                ? practice.getScore().getTitle()
                : practice.getContent().getTitle();

        return new PracticeResponse(
                practice.getId(),
                practice.getScore() != null ? practice.getScore().getId() : null,
                practice.getContent() != null ? practice.getContent().getId() : null,
                title,
                practice.getPitchScore(),
                practice.getRhythmScore(),
                practice.getPostureScore(),
                practice.getTotalScore(),
                practice.getDurationSeconds(),
                practice.getIssues().stream().map(IssueResponse::from).toList(),
                practice.getCreatedAt()
        );
    }
}