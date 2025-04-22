package app.project.jjoojjeollee.domain.user;

import app.project.jjoojjeollee.domain.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(name="uk_users_email", columnNames = {"email"}),
        @UniqueConstraint(name="uk_users_id", columnNames = {"id"})
})
@SequenceGenerator( name = "user_seq_generator",
                    sequenceName = "users_seq",
                    initialValue = 1,
                    allocationSize = 1
                    )
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

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserProfile profile;

    /**
     *  회원가입 시 회원 생성 메서드
     */
    public static User createUser(String id, String pw, String email){
        User user = new User();
        user.setId(id);
        user.setPw(pw);
        user.setEmail(email);
        user.setCreatedAt(LocalDateTime.now());
        // 생성 시 기본값은 N
        user.setEmailVerified("N");
        return user;
    }

    /**
     * 사용할 수 있는 사용자인지 체크
     */
    @Transient
    public UserStatus getUserStatus(){
        if (this.withdrawnAt != null){
            long daysDiff = ChronoUnit.DAYS.between(LocalDateTime.now(), this.withdrawnAt);
            if(daysDiff > 30){
                return UserStatus.WITHDRAWN;
            }else{
                return UserStatus.WITHDRAW_PENDING;
            }
        } else if (this.lockedAt != null) {
            return UserStatus.LOCKED;
        } else if (this.emailVerified.equals("N")) {
            return UserStatus.EMAIL_NOT_VERIFIED;
        }

        return UserStatus.ACTIVE;
    }
    /**
     * 사용자 탈퇴 메서드
     */
    public void withdraw(){
        this.emailVerified = "N";
        this.withdrawnAt = LocalDateTime.now();
    }

    /**
     * 재인증 메서드
     */
    public void verify(){
        this.emailVerified = "Y";
        this.withdrawnAt = null;
        this.lockedAt = null;
    }

    /**
     * 사용자 잠금 메서드
     */
    public void lock(){
        this.emailVerified = "N";
        this.lockedAt = LocalDateTime.now();
    }

    /**
     * 프로필 설정 메서드
     */
    public void setupUserProfile(String nickname, String lineMessage, Image image){
        if(this.profile == null){
            this.profile = UserProfile.createUserProfile(nickname, lineMessage, image);
            return;
        }
        // 프로필 값이 있는 경우
        this.profile.updateUserProfile(nickname, lineMessage, image);
    }


}
