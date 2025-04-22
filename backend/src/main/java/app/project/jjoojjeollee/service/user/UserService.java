package app.project.jjoojjeollee.service.user;

import app.project.jjoojjeollee.domain.Image;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.domain.user.UserStatus;
import app.project.jjoojjeollee.param.user.UserLoginParam;
import app.project.jjoojjeollee.param.user.UserProfileSettupParam;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 메서드
     */
    @Transactional
    public Long join(UserRegisterParam userRegisterParam){
        User user = User.createUser( userRegisterParam.getId(),
                                     passwordEncoder.encode(userRegisterParam.getPw()),
                                     userRegisterParam.getEmail()
                                    );
        // email 인증 로직 미구현으로 우선 항상 인증처리 함
        user.verify();
        return userRepository.save(user);
    }
    /**
     *  로그인 메서드
     */
    public Long login(UserLoginParam loginParam){
        User findUser;

        if(loginParam.isEmail()){
            String email = loginParam.getId();
            findUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        }else{
            String id = loginParam.getId();
            findUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        }

        String pw = loginParam.getPw();
        if( !(passwordEncoder.matches(pw, findUser.getPw())) ) {
            throw new PasswordMismatchException();
        }

        UserStatus status = findUser.getUserStatus();
        if(!status.isAccessible()){
            if(status.isRecoverable()){
                throw new RecoverableUserException(status);
            }
            throw new WithdrawnUserException();
        }

        return findUser.getNo();
    }

    /**
     * 사용자 탈퇴 요청 메서드
     */
    @Transactional
    public void withdraw(Long userNo){
        User user = userRepository.findByNo(userNo);
        user.withdraw();
    }

    /**
     * 사용자 상태 복구 메서드 
     */
    @Transactional
    public void recover(Long userNo){
        User user = userRepository.findByNo(userNo);
        user.verify();
    }

    /**
     * 프로필 설정 메서드
     */
    @Transactional
    public void saveUserProfile(Long userNo, UserProfileSettupParam param, Image image){
        User user = userRepository.findWithProfileByNo(userNo).orElse(userRepository.findByNo(userNo));

        user.setupUserProfile(param.getNickname(),
                              param.getLineMessage(),
                              image);

    }

    /**
     * 프로필 조회 메서드
     */
    public User findUserWithProfile(Long userNo){
        return userRepository.findWithProfileByNo(userNo).orElseThrow(UserNotFoundException::new);
    }

    /**
     * 프로필 이미지 삭제 메서드
     */
    public String removeUserProfileImage(Long userNo){
        User user = userRepository.findWithProfileByNo(userNo).orElseThrow(UserNotFoundException::new);
        String relativeFileName = user.getProfile().getProfileImage().getRelativePath();
        user.getProfile().removeUserProfileImage();

        return relativeFileName;
    }
}
