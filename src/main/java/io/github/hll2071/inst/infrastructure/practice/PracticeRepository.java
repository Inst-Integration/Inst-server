package io.github.hll2071.inst.infrastructure.practice;

import io.github.hll2071.inst.domain.practice.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PracticeRepository extends JpaRepository<Practice, Long> {

    List<Practice> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT p FROM Practice p LEFT JOIN FETCH p.issues WHERE p.id = :id")
    Optional<Practice> findByIdWithIssues(@Param("id") Long id);
}