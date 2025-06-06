package app.project.jjoojjeollee.domain;

import app.project.jjoojjeollee.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "notifications")
@Getter
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_no")
    private Long no;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "url")
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_no")
    private User target;
}
