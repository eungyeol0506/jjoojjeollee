package app.project.jjoojjeollee.domain;

import app.project.jjoojjeollee.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_read")
@Getter
public class NotificationRead {
    @Id @GeneratedValue
    @Column(name = "read_no")
    private Long no;

    @Column(name = "read_at", nullable = false)
    private LocalDateTime readAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_no", nullable = false)
    private Notification notification;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User readBy;

}
