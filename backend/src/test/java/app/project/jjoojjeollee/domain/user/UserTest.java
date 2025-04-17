package app.project.jjoojjeollee.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        User user = User.createUser(id, pw, email);

        // then
        assertNotNull(user);
        assertEquals("Y", user.getEmailVerified(), "회원가입 시 이메일 인증여부가 Y임");
        assertEquals(LocalDateTime.now().getDayOfMonth(), user.getCreatedAt().getDayOfMonth(), "생성날짜가 오늘임");
    }
}