package io.github.hll2071.inst.application.practice;

import io.github.hll2071.inst.domain.practice.Practice;
import io.github.hll2071.inst.infrastructure.practice.PracticeRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetPracticeDetailUseCase {

    private final PracticeRepository practiceRepository;

    @Transactional(readOnly = true)
    public Practice execute(Long practiceId) {
        return practiceRepository.findByIdWithIssues(practiceId)
                .orElseThrow(() -> new InstException(ErrorCode.PRACTICE_NOT_FOUND));
    }
}