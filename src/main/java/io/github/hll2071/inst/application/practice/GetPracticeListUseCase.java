package io.github.hll2071.inst.application.practice;

import io.github.hll2071.inst.domain.practice.Practice;
import io.github.hll2071.inst.infrastructure.practice.PracticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPracticeListUseCase {

    private final PracticeRepository practiceRepository;

    @Transactional(readOnly = true)
    public List<Practice> execute(Long userId) {
        return practiceRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}