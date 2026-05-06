package io.github.hll2071.inst.application.teacher;

import io.github.hll2071.inst.domain.teacher.Teacher;
import io.github.hll2071.inst.infrastructure.teacher.TeacherRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetTeacherDetailUseCase {

    private final TeacherRepository teacherRepository;

    @Transactional(readOnly = true)
    public Teacher execute(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new InstException(ErrorCode.TEACHER_NOT_FOUND));
    }
}