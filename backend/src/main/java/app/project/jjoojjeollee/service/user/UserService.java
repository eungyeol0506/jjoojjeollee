package app.project.jjoojjeollee.service.user;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.domain.user.UserStatus;
import app.project.jjoojjeollee.param.user.UserLoginParam;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 메서드
     */
    public Long join(UserRegisterParam userRegisterParam){
        User user = User.createUser( userRegisterParam.getId(),
                                     passwordEncoder.encode(userRegisterParam.getPw()),
                                     userRegisterParam.getEmail()
                                    );
        // email 인증 로직 미구현으로 우선 항상 인증처리 함
        user.verify();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User login(UserLoginParam loginParam){
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

        return findUser;
    }

    public void withdraw(Long userNo){
        User user = userRepository.findByNo(userNo);
        user.withdraw();
    }

    public void recover(Long userNo){
        User user = userRepository.findByNo(userNo);
        user.verify();
    }


}
