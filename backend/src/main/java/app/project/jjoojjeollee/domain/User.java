package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id @GeneratedValue
    @Column(name="user_no")
    private Long no;
    @Column(name="user_id")
    private String id;
    @Column(name="user_pw")
    private String pw;
    private String email;
    private Boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime withdrawnAt;
    private LocalDateTime lockedAt;

    @OneToOne
    private UserProfile profile;
}
