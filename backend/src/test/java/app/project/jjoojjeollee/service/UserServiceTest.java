package app.project.jjoojjeollee.service;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.param.user.UserLoginParam;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import app.project.jjoojjeollee.service.user.PasswordMismatchException;
import app.project.jjoojjeollee.service.user.RecoverableUserException;
import app.project.jjoojjeollee.service.user.UserNotFoundException;
import app.project.jjoojjeollee.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    
    @DisplayName("회원가입 성공")
    @Test
    public void join() throws Exception{
        //given
        UserRegisterParam userRegisterParam = new UserRegisterParam("test", "test", "test@test.com");

        //when
        Long userNo = userService.join(userRegisterParam);

        //then
        User user = userRepository.findByNo(userNo);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(userRegisterParam.getId());
        Assertions.assertThat(passwordEncoder.matches(userRegisterParam.getPw(), user.getPw())).isTrue();
    }
    
    @Nested
    class login{
        @Test
        @DisplayName("로그인 성공")
        public void loginByEmail() throws Exception{
            //given
            userService.join(new UserRegisterParam("test", "password", "test@test.com"));

            UserLoginParam userLoginParamId = new UserLoginParam("test", "password");
            UserLoginParam userLoginParamEmail = new UserLoginParam("test@test.com", "password");
            //when
            User result1 = userService.login(userLoginParamId);
            User result2 = userService.login(userLoginParamEmail);
            //then
            Assertions.assertThat(result1).isNotNull();
            Assertions.assertThat(result2).isNotNull();
            Assertions.assertThat(result1).isEqualTo(result2);
        }
        
        @DisplayName("존재하지 않는 사용자로 로그인 실패")
        @Test
        public void failedWithNonExistUser() throws Exception{
            //given
            UserLoginParam userLoginParam = new UserLoginParam("test", "password");

            //when
            Throwable exception = Assertions.catchException(() -> userService.login(userLoginParam));

            //then
            Assertions.assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("비밀번호 불일치로 로그인 실패")
        @Test
        public void failedWithMismatchedPassword() throws Exception {
            //given
            userService.join(new UserRegisterParam("test", "password", "test@test.com"));

            UserLoginParam userLoginParam = new UserLoginParam("test", "password2");
            //when
            Throwable exception = Assertions.catchException(() -> userService.login(userLoginParam));
            //then
            Assertions.assertThat(exception).isInstanceOf(PasswordMismatchException.class);
        }

        @DisplayName("사용가능하지 않은 사용자 상태로 실패")
        @Test
        public void failedWithUnverifiedUser() throws Exception{
            //given
            Long userNo = userService.join(new UserRegisterParam("test", "password", "test@test.com"));
            userService.withdraw(userNo);

            UserLoginParam userLoginParam = new UserLoginParam("test", "password");
            //when
            Throwable exception = Assertions.catchException(() -> userService.login(userLoginParam));
            //then
            Assertions.assertThat(exception).isInstanceOf(RecoverableUserException.class);
        }
    }
}