package io.github.hll2071.inst.infrastructure.tmi;

import io.github.hll2071.inst.domain.tmi.Tmi;
import io.github.hll2071.inst.domain.tmi.TmiCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
}