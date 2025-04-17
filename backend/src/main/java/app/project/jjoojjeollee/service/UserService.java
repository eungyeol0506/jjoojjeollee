package app.project.jjoojjeollee.service;

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

    public Long join(UserRegisterParam userRegisterParam){
        User user = User.createUser( userRegisterParam.getId(),
                                     passwordEncoder.encode(userRegisterParam.getPw()),
                                     userRegisterParam.getEmail()
                                    );
        user.verify();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User login(UserLoginParam loginParam){
        User findUser;

        if(loginParam.isEmail()){
            String email = loginParam.getId();
            findUser = userRepository.findByEmail(email).orElseThrow(
                    ()->new IllegalStateException("사용자가 존재하지 않음")
            );
        }else{
            String id = loginParam.getId();
            findUser = userRepository.findById(id).orElseThrow(
                    ()->new IllegalStateException("사용자가 존재하지 않음")
            );
        }

        String pw = loginParam.getPw();
        if( !(passwordEncoder.matches(pw, findUser.getPw())) ) {
            throw new IllegalStateException("비밀번호 매칭되지 않음");
        }

        UserStatus status = findUser.getUserStatus();
        if(! status.equals(UserStatus.VERIFIED) ){
            throw new IllegalStateException("사용 불가능한 상태");
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
