package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class NotificationRead {
    @Id @GeneratedValue
    @Column(name="read_no")
    private Long no;

    @OneToOne
    private Notification notification;

    @OneToOne
    private User readBy;

    private LocalDateTime readAt;
}
