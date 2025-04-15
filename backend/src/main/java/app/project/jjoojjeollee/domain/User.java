package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(name="uk_users_email", columnNames = {"email"}),
        @UniqueConstraint(name="uk_users_id", columnNames = {"id"})
})
@Getter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_no", updatable = false)
    private Long no;

    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name="email_verified", nullable = false)
    private Boolean emailVerified;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="withdrawn_at")
    private LocalDateTime withdrawnAt;
    @Column(name="locked_at")
    private LocalDateTime lockedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserProfile profile;
}
