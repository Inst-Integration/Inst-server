package io.github.hll2071.inst.application.tmi;

import io.github.hll2071.inst.domain.tmi.TmiQuiz;
import io.github.hll2071.inst.domain.tmi.TmiQuizOption;
import io.github.hll2071.inst.infrastructure.tmi.TmiQuizRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubmitTmiQuizUseCase {

    private final TmiQuizRepository tmiQuizRepository;

    public record Result(boolean correct) {}

    @Transactional(readOnly = true)
    public Result execute(Long quizId, Long optionId) {
        TmiQuiz quiz = tmiQuizRepository.findById(quizId)
                .orElseThrow(() -> new InstException(ErrorCode.QUIZ_NOT_FOUND));

        boolean correct = quiz.getOptions().stream()
                .filter(o -> o.getId().equals(optionId))
                .findFirst()
                .map(TmiQuizOption::isCorrect)
                .orElseThrow(() -> new InstException(ErrorCode.INVALID_INPUT));

        return new Result(correct);
    }
}