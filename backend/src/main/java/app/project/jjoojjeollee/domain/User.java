package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(name="uk_users_email", columnNames = {"email"}),
        @UniqueConstraint(name="uk_users_id", columnNames = {"id"})
})
@SequenceGenerator( name = "user_seq_generator",
                    sequenceName = "users_seq",
                    initialValue = 1,
                    allocationSize = 1
                    )
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGenerator")
    @Column(name = "user_no", updatable = false)
    private Long no;

    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name="email_verified", nullable = false, length = 1)
    private String emailVerified;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="withdrawn_at")
    private LocalDateTime withdrawnAt;
    @Column(name="locked_at")
    private LocalDateTime lockedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserProfile profile;
}
