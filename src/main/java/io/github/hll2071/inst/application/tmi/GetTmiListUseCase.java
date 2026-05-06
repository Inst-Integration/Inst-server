package io.github.hll2071.inst.application.tmi;

import io.github.hll2071.inst.domain.tmi.Tmi;
import io.github.hll2071.inst.domain.tmi.TmiCategory;
import io.github.hll2071.inst.infrastructure.tmi.TmiRepository;
import io.github.hll2071.inst.infrastructure.tmi.UserTmiInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTmiListUseCase {

    private static final int DAILY_TMI_COUNT = 3;
    private final TmiRepository tmiRepository;
    private final UserTmiInterestRepository userTmiInterestRepository;

    @Transactional(readOnly = true)
    public List<Tmi> execute(Long userId) {
        List<String> categories = userTmiInterestRepository.findByUserId(userId).stream()
                .map(i -> i.getCategory().name())
                .toList();

        if (categories.isEmpty()) {
            return tmiRepository.findRandom(DAILY_TMI_COUNT);
        }

        return tmiRepository.findRandomByCategories(categories, DAILY_TMI_COUNT);
    }
}