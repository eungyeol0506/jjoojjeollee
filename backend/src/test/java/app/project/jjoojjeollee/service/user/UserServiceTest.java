package app.project.jjoojjeollee.service.user;

import app.project.jjoojjeollee.domain.Image;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.dto.user.UserLoginParam;
import app.project.jjoojjeollee.dto.user.UserProfileSettupParam;
import app.project.jjoojjeollee.dto.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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
        User user = userRepository.findByNo(userNo).get();
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(userRegisterParam.getId());
        Assertions.assertThat(passwordEncoder.matches(userRegisterParam.getPw(), user.getPw())).isTrue();
    }
    
    @Nested
    class Login{
        @Test
        @DisplayName("로그인 성공")
        public void loginByEmail() throws Exception{
            //given
            userService.join(new UserRegisterParam("test", "password", "test@test.com"));

            UserLoginParam userLoginParamId = new UserLoginParam("test", "password");
            UserLoginParam userLoginParamEmail = new UserLoginParam("test@test.com", "password");
            //when
            Long result1 = userService.login(userLoginParamId);
            Long result2 = userService.login(userLoginParamEmail);
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

    @Nested
    @DisplayName("프로필 관리 테스트")
    class manageProfile{
        @DisplayName("프로필 저장 성공 후 조회 (이미지 수정 있음)")
        @Test
        public void successSetupUserProfileWithNew() throws Exception{
            //given
            Long userNo = userService.join(new UserRegisterParam("test", "password", "test@test.com"));
            UserProfileSettupParam param = new UserProfileSettupParam("nickname", "");
            Image image = Image.createImage("filename", "savedName", "path", "png");

            //when
            userService.saveUserProfile(userNo, param, image);

            //then
            User user = userService.findUserWithProfile(userNo);
            assertNotNull(user.getProfile().getProfileImage(), "이미지가 설정됨");
            assertEquals("nickname", user.getProfile().getNickname(), "별명이 설정됨");
            assertNull(user.getProfile().getLineMessage(), "공백 메시지는 null임");
        }

        @Test
        @DisplayName("이미지 수정없이 닉네임/메시지만 수정하는 경우")
        public void successSetupUserProfile() throws Exception{
            //given
            Long userNo = userService.join(new UserRegisterParam("test", "password", "test@test.com"));
            UserProfileSettupParam param = new UserProfileSettupParam("nickname", "");
            Image image = Image.createImage("filename", "savedName", "path", "png");
            userService.saveUserProfile(userNo, param, image);

            UserProfileSettupParam newParam = new UserProfileSettupParam("new nickname", "new message");
            //when
            userService.saveUserProfile(userNo, newParam, null);

            //then
            User user = userService.findUserWithProfile(userNo);
            assertNotNull(user.getProfile().getProfileImage(), "이미지값 유지됨");
            assertEquals("new nickname", user.getProfile().getNickname(), "별명이 설정됨");
            assertEquals("new message", user.getProfile().getLineMessage(), "별명이 설정됨");
        }
        
        @Test
        public void successRemoveUserProfile() throws Exception{
            //given
            Long userNo = userService.join(new UserRegisterParam("test", "password", "test@test.com"));
            Image image = Image.createImage("filename", "savedName", "path", "png");
            userService.saveUserProfile(userNo, new UserProfileSettupParam("nickname", "new message"), image);

            //when
            String path = userService.removeUserProfileImage(userNo);

            //then
            User user = userService.findUserWithProfile(userNo);
            assertEquals(image.getRelativePath(), path, "삭제된 이미지와 삭제할 경로가 똑같음");
            assertNotNull(user.getProfile(), "프로필은 삭제되지 않음");
            assertNull(user.getProfile().getProfileImage(), "이미지 값이 null");
            assertEquals("nickname", user.getProfile().getNickname(), "별명이 설정됨");
            assertEquals("new message", user.getProfile().getLineMessage(), "한줄메시지가 설정됨");

        }
    }
}