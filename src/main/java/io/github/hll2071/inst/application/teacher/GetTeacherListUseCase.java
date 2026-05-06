package io.github.hll2071.inst.application.teacher;

import io.github.hll2071.inst.domain.teacher.Teacher;
import io.github.hll2071.inst.infrastructure.teacher.TeacherRepository;
import io.github.hll2071.inst.shared.enums.Instrument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTeacherListUseCase {

    private final TeacherRepository teacherRepository;

    @Transactional(readOnly = true)
    public List<Teacher> execute(Instrument instrument) {
        if (instrument != null) {
            return teacherRepository.findByInstrumentOrderByRatingDesc(instrument);
        }
        return teacherRepository.findAllByOrderByRatingDesc();
    }
}