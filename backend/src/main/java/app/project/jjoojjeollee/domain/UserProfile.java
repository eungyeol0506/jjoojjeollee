package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "user_profiles")
@Getter
public class UserProfile {
    @Id @GeneratedValue
    @Column(name = "profile_no")
    private Long no;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "line_message", length = 80)
    private String lineMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_no")
    private Image profileImage;
}
