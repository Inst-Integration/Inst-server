package io.github.hll2071.inst.application.content;

import io.github.hll2071.inst.domain.content.Content;
import io.github.hll2071.inst.domain.content.ContentLevel;
import io.github.hll2071.inst.infrastructure.content.ContentRepository;
import io.github.hll2071.inst.infrastructure.content.UserContentProgressRepository;
import io.github.hll2071.inst.shared.enums.Instrument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetContentListUseCase {

    private final ContentRepository contentRepository;
    private final UserContentProgressRepository userContentProgressRepository;

    public record ContentItem(Content content, boolean completed) {}

    @Transactional(readOnly = true)
    public List<ContentItem> execute(Long userId, Instrument instrument, ContentLevel level) {
        List<Content> contents = contentRepository
                .findByInstrumentAndLevelOrderByOrderIndex(instrument, level);

        Set<Long> completedIds = userContentProgressRepository.findByUserId(userId).stream()
                .map(p -> p.getContent().getId())
                .collect(Collectors.toSet());

        return contents.stream()
                .map(c -> new ContentItem(c, completedIds.contains(c.getId())))
                .toList();
    }
}