package io.github.hll2071.inst.infrastructure.teacher;

import io.github.hll2071.inst.domain.teacher.TeacherInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherInquiryRepository extends JpaRepository<TeacherInquiry, Long> {
}