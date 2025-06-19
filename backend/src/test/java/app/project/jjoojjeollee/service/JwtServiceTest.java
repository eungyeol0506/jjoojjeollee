package app.project.jjoojjeollee.service;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("JwtService Test")
@Transactional
class JwtServiceTest {
    @Autowired private JwtService jwtService;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("토큰 생성 및 parsing 성공")
    public void createAndParseToken() throws Exception{
        //given
        User user = User.createUser("email", "pw");
        Long userNo = userRepository.save(user);

        //when
        String token = jwtService.toToken(userNo);
        Long subject = jwtService.getSubFromToken(token).orElse(0L);

        //then
        assertNotNull(token, "토큰값이 생성됨");
        assertNotEquals(0L, subject, "subject 추출 성공");
        assertEquals(userNo, subject, "추출된 no가 테스트 userNo 값이랑 동일");
    }

    @Test
    @DisplayName("토큰 추출 실패하는 경우")
    public void invalidToken() throws Exception{
        //given
        User user = User.createUser("email", "pw");
        Long userNo = userRepository.save(user);
        User user2 = User.createUser("email", "pw");
        Long userNo2 = userRepository.save(user2);

        String token = jwtService.toToken(userNo);
        String invalidToken = "abc.def.ght"; // 엉터리 토큰
        String invalidToken2 = jwtService.toToken(userNo2); //완전 다른 토큰
        //when
        Long subject = jwtService.getSubFromToken(token).orElse(0L);
        Long subject2 = jwtService.getSubFromToken(invalidToken).orElse(0L);
        Long subject3 = jwtService.getSubFromToken(invalidToken2).orElse(0L);

        //then
        assertNotEquals(subject, subject2);
        assertNotEquals(subject, subject3);
        assertNotEquals(userNo, subject2);
        assertNotEquals(userNo, subject3);
    }
}