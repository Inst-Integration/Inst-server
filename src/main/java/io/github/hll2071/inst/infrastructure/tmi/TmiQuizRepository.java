package io.github.hll2071.inst.infrastructure.tmi;

import io.github.hll2071.inst.domain.tmi.TmiQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TmiQuizRepository extends JpaRepository<TmiQuiz, Long> {

    @Query("SELECT q FROM TmiQuiz q JOIN FETCH q.options WHERE q.tmi.id = :tmiId")
    List<TmiQuiz> findByTmiIdWithOptions(@Param("tmiId") Long tmiId);
}