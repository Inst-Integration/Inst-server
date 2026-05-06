package io.github.hll2071.inst.domain.content;

import io.github.hll2071.inst.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_content_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "content_id"}))
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContentProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime completedAt;

    @Builder
    private UserContentProgress(User user, Content content) {
        this.user = user;
        this.content = content;
    }
}