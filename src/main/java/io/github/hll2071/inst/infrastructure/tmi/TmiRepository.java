package io.github.hll2071.inst.infrastructure.tmi;

import io.github.hll2071.inst.domain.tmi.Tmi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TmiRepository extends JpaRepository<Tmi, Long> {

    @Query(value = """
            SELECT * FROM tmis
            WHERE category IN :categories
            ORDER BY RANDOM()
            LIMIT :limit
            """, nativeQuery = true)
    List<Tmi> findRandomByCategories(
            @Param("categories") List<String> categories,
            @Param("limit") int limit);

    @Query(value = "SELECT * FROM tmis ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Tmi> findRandom(@Param("limit") int limit);

    @Query(value = "SELECT * FROM tmis t WHERE EXISTS (SELECT 1 FROM tmi_quizzes q WHERE q.tmi_id = t.id) ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Tmi> findRandomWithQuiz();

    @Query(value = "SELECT * FROM tmis t WHERE t.category = :category AND EXISTS (SELECT 1 FROM tmi_quizzes q WHERE q.tmi_id = t.id) ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Tmi> findRandomByCategory(@Param("category") String category);
}