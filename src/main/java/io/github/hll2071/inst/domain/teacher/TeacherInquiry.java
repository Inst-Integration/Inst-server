package io.github.hll2071.inst.domain.teacher;

import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher_inquiries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherInquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryStatus status;

    @Builder
    private TeacherInquiry(User user, Teacher teacher, String message, InquiryStatus status) {
        this.user = user;
        this.teacher = teacher;
        this.message = message;
        this.status = status;
    }
}