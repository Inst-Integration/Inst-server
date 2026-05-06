package io.github.hll2071.inst.application.content;

import io.github.hll2071.inst.domain.content.Content;
import io.github.hll2071.inst.infrastructure.content.ContentRepository;
import io.github.hll2071.inst.infrastructure.content.UserContentProgressRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetContentDetailUseCase {

    private final ContentRepository contentRepository;
    private final UserContentProgressRepository userContentProgressRepository;

    public record Result(Content content, boolean completed) {}

    @Transactional(readOnly = true)
    public Result execute(Long userId, Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new InstException(ErrorCode.CONTENT_NOT_FOUND));

        boolean completed = userContentProgressRepository
                .existsByUserIdAndContentId(userId, contentId);

        return new Result(content, completed);
    }
}