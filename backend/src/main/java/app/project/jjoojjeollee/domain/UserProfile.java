package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

@Entity
public class UserProfile {
    @Id @GeneratedValue
    @Column(name="profile_no")
    private Long no;
    private String nickname;
    private String lineMessage;

    @OneToOne
    private Image profileImage;
}
