package app.project.jjoojjeollee.service;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long join(UserRegisterParam userRegisterParam){
        User user = User.createUser( userRegisterParam.getId(),
                                     passwordEncoder.encode(userRegisterParam.getPw()),
                                     userRegisterParam.getEmail()
                                    );
        // 중복여부 검사
        return userRepository.save(user);
    }

//    public User loginById(String id, String password){
//        User user = userRepository.findById(id).get();
//        if(user != null){
//            return null;
//            //"해당되는 유저가 없음"
//        }
//        if(passwordEncoder.matches(password, user.getPw())){
//            return user;
//        }
//        //비밀번호 불일치
//        return null;
//    }

    public User loginByEmail(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(
                                ()->new IllegalStateException("사용자가 존재하지 않음"));
        if(passwordEncoder.matches(password, user.getPw())){
            return user;
        }
        throw new IllegalStateException("비밀번호 매칭되지 않음");
    }

}
