package io.github.hll2071.inst.infrastructure.tmi;

import io.github.hll2071.inst.domain.tmi.TmiCategory;
import io.github.hll2071.inst.domain.tmi.UserTmiInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTmiInterestRepository extends JpaRepository<UserTmiInterest, Long> {
    List<UserTmiInterest> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}