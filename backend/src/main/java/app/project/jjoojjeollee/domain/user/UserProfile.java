package app.project.jjoojjeollee.domain.user;

import app.project.jjoojjeollee.domain.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    public static UserProfile createUserProfile(String nickname, String lineMessage, Image profileImage) {
        UserProfile userProfile = new UserProfile();
        userProfile.setNickname(nickname);
        userProfile.setLineMessage(lineMessage);
        userProfile.setProfileImage(profileImage);
        return userProfile;
    }
}
