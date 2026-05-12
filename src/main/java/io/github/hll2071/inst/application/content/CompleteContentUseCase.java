package io.github.hll2071.inst.application.content;

import io.github.hll2071.inst.domain.content.Content;
import io.github.hll2071.inst.domain.content.UserContentProgress;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.content.ContentRepository;
import io.github.hll2071.inst.infrastructure.content.UserContentProgressRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompleteContentUseCase {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final UserContentProgressRepository userContentProgressRepository;

    @Transactional
    public void execute(Long userId, Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new InstException(ErrorCode.CONTENT_NOT_FOUND));

        // 이미 수료
        if (userContentProgressRepository.existsByUserIdAndContentId(userId, contentId)) {
            return;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        userContentProgressRepository.save(
                UserContentProgress.builder()
                        .user(user)
                        .content(content)
                        .build()
        );
    }
}