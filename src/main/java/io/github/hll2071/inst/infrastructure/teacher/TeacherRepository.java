package io.github.hll2071.inst.infrastructure.teacher;

import io.github.hll2071.inst.domain.teacher.Teacher;
import io.github.hll2071.inst.domain.teacher.TeacherInquiry;
import io.github.hll2071.inst.shared.enums.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByInstrumentOrderByRatingDesc(Instrument instrument);
    List<Teacher> findAllByOrderByRatingDesc();
}