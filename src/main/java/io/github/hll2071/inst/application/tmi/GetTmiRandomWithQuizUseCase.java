package io.github.hll2071.inst.application.tmi;

import io.github.hll2071.inst.domain.tmi.Tmi;
import io.github.hll2071.inst.infrastructure.tmi.TmiRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetTmiRandomWithQuizUseCase {

    private final TmiRepository tmiRepository;

    @Transactional(readOnly = true)
    public Tmi execute() {
        return tmiRepository.findRandomWithQuiz()
                .orElseThrow(() -> new InstException(ErrorCode.TMI_NOT_FOUND));
    }
}