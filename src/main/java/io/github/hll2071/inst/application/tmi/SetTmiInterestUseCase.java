package io.github.hll2071.inst.application.tmi;

import io.github.hll2071.inst.domain.tmi.TmiCategory;
import io.github.hll2071.inst.domain.tmi.UserTmiInterest;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.tmi.UserTmiInterestRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetTmiInterestUseCase {

    private final UserTmiInterestRepository userTmiInterestRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long userId, List<TmiCategory> categories) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        // 기존 관심사 전체 삭제 후 새로 저장
        userTmiInterestRepository.deleteByUserId(userId);

        List<UserTmiInterest> interests = categories.stream()
                .map(category -> UserTmiInterest.builder()
                        .user(user)
                        .category(category)
                        .build())
                .toList();

        userTmiInterestRepository.saveAll(interests);
    }
}