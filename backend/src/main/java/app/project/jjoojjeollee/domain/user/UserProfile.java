package app.project.jjoojjeollee.domain.user;

import app.project.jjoojjeollee.domain.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_no")
    private Image profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    /**
     * 프로필 생성 메서드
     */
    public static UserProfile createUserProfile(String nickname, String lineMessage, Image profileImage) {
        UserProfile userProfile = new UserProfile();
        userProfile.setNickname(nickname);
        userProfile.setLineMessage(StringUtils.hasText(lineMessage) ? lineMessage : null);
        userProfile.setProfileImage(profileImage);
        return userProfile;
    }

    /**
     * 기존 프로필 수정 메서드
     */
    public void updateUserProfile(String nickname, String lineMessage, Image profileImage) {
        this.nickname = nickname;
        this.lineMessage = StringUtils.hasText(lineMessage) ? lineMessage : null;
        // null 인 경우 파일을 변경하는 경우가 아니므로
        if(profileImage != null){
            this.profileImage = profileImage;
        }
    }

    /**
     * 프로필 이미지 삭제 메서드
     */
    public void removeUserProfileImage() {
        this.profileImage = null;
    }

}
