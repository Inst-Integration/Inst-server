package io.github.hll2071.inst.application.tmi;

import io.github.hll2071.inst.domain.tmi.Tmi;
import io.github.hll2071.inst.domain.tmi.TmiQuiz;
import io.github.hll2071.inst.infrastructure.tmi.TmiQuizRepository;
import io.github.hll2071.inst.infrastructure.tmi.TmiRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTmiDetailUseCase {

    private final TmiRepository tmiRepository;
    private final TmiQuizRepository tmiQuizRepository;

    public record Result(Tmi tmi, List<TmiQuiz> quizzes) {}

    @Transactional(readOnly = true)
    public Result execute(Long tmiId) {
        Tmi tmi = tmiRepository.findById(tmiId)
                .orElseThrow(() -> new InstException(ErrorCode.TMI_NOT_FOUND));

        List<TmiQuiz> quizzes = tmiQuizRepository.findByTmiIdWithOptions(tmiId);
        return new Result(tmi, quizzes);
    }
}