package io.github.hll2071.inst.infrastructure.content;

import io.github.hll2071.inst.domain.content.UserContentProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserContentProgressRepository extends JpaRepository<UserContentProgress, Long> {
    List<UserContentProgress> findByUserId(Long userId);
    Optional<UserContentProgress> findByUserIdAndContentId(Long userId, Long contentId);
    boolean existsByUserIdAndContentId(Long userId, Long contentId);
}