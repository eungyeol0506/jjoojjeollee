package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

@Entity
public class Notification {
    @Id @GeneratedValue
    @Column(name="notification_no")
    private Long no;

    @OneToOne
    private User target;

    private String message;
    private String url;
}
