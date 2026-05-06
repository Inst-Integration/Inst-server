package io.github.hll2071.inst.application.teacher;

import io.github.hll2071.inst.domain.teacher.InquiryStatus;
import io.github.hll2071.inst.domain.teacher.Teacher;
import io.github.hll2071.inst.domain.teacher.TeacherInquiry;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.teacher.TeacherInquiryRepository;
import io.github.hll2071.inst.infrastructure.teacher.TeacherRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateTeacherInquiryUseCase {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherInquiryRepository teacherInquiryRepository;

    public record Command(Long userId, Long teacherId, String message) {}

    @Transactional
    public void execute(Command command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        Teacher teacher = teacherRepository.findById(command.teacherId())
                .orElseThrow(() -> new InstException(ErrorCode.TEACHER_NOT_FOUND));

        teacherInquiryRepository.save(
                TeacherInquiry.builder()
                        .user(user)
                        .teacher(teacher)
                        .message(command.message())
                        .status(InquiryStatus.PENDING)
                        .build()
        );
    }
}