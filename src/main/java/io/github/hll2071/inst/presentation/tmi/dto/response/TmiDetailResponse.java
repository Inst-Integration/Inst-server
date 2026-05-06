package io.github.hll2071.inst.presentation.tmi.dto.response;

import io.github.hll2071.inst.domain.tmi.Tmi;
import io.github.hll2071.inst.domain.tmi.TmiCategory;
import io.github.hll2071.inst.domain.tmi.TmiQuiz;
import io.github.hll2071.inst.domain.tmi.TmiQuizOption;

import java.util.List;

public record TmiDetailResponse(
        Long id,
        String title,
        String content,
        TmiCategory category,
        List<QuizResponse> quizzes
) {
    public record QuizResponse(
            Long id,
            String question,
            List<OptionResponse> options
    ) {
        public record OptionResponse(
                Long id,
                String content
        ) {
            public static OptionResponse from(TmiQuizOption option) {
                return new OptionResponse(option.getId(), option.getContent());
            }
        }

        public static QuizResponse from(TmiQuiz quiz) {
            return new QuizResponse(
                    quiz.getId(),
                    quiz.getQuestion(),
                    quiz.getOptions().stream().map(OptionResponse::from).toList()
            );
        }
    }

    public static TmiDetailResponse from(Tmi tmi, List<TmiQuiz> quizzes) {
        return new TmiDetailResponse(
                tmi.getId(),
                tmi.getTitle(),
                tmi.getContent(),
                tmi.getCategory(),
                quizzes.stream().map(QuizResponse::from).toList()
        );
    }
}