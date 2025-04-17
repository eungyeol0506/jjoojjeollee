package app.project.jjoojjeollee.service;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    
    @DisplayName("사용자 회원가입 메서드 성공")
    @Test
    public void join() throws Exception{
        //given
        UserRegisterParam userRegisterParam = new UserRegisterParam("test", "test", "test@test.com");
        Long id = 1L;

        given(userRepository.save(any(User.class))).willReturn(id);

        //when
        Long testId = userService.join(userRegisterParam);

        //then
        assertEquals(id, testId, "같은 아이디값");

    }
}