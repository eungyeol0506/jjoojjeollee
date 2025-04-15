package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user_profiles")
@SequenceGenerator( name = "userProfile_seq_generator",
        sequenceName = "user_profiles_seq",
        initialValue = 1,
        allocationSize = 1
)
public class UserProfile {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userProfile_seq_generator")
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
