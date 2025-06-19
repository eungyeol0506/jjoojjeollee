package app.project.jjoojjeollee.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {
    @Test
    @DisplayName("사용자 도메인 회원가입 메서드 테스트")
    void createUser(){
        // given
        String id = "test";
        String pw = "test";
        String email = "test@test.com";

        // when
        User user = User.createUser(email, pw);

        // then
        assertNotNull(user);
        assertEquals(LocalDateTime.now().getDayOfMonth(), user.getCreatedAt().getDayOfMonth(), "생성날짜가 오늘임");
    }
    
    @DisplayName("사용자 도메인 생성 시 프로필 settup 테스트")
    @Test
    public void setupProfile() throws Exception{
        // given
        String id = "test";
        String pw = "test";
        String email = "test@test.com";

        String nickname = "테스트!";
        User user = User.createUser(email, pw);
        //when then Create 메서드 테스트
        user.setupUserProfile(nickname,"",  null);
        assertNotNull(user.getProfile(), "프로필 객체 생성됨");
        assertNull(user.getProfile().getLineMessage(), "lineMessage 값 비어있음");
        //when then Update 메서드 테스트
        user.setupUserProfile(nickname,"새 메시지 set",  null);
        assertNotNull(user.getProfile(), "프로필 객체 생성됨");
        assertNotNull(user.getProfile().getLineMessage(), "메시지 setup 됨");
    }
    
}