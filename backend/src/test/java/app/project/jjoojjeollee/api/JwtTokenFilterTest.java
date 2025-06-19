package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.repository.UserRepository;
import app.project.jjoojjeollee.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtFilter Unit Test")
class JwtTokenFilterTest {
    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Captor
    ArgumentCaptor<UsernamePasswordAuthenticationToken> authCaptor;

    @BeforeEach
    void setup() {
        jwtTokenFilter = new JwtTokenFilter(userRepository, jwtService, new ObjectMapper());
    }

    @Test
    @DisplayName("토큰이 정상적으로 주어졌을 때 filter 동작 확인")
    public void successCreateToken() throws Exception{
        //given
        User user = User.createUser("test@email.com", "test");
        String token = "test.jwt.token";
        Long userNo = 1234L;

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.addHeader("Authorization", "Bearer " + token);

        FilterChain mockChain = mock(FilterChain.class);
        BDDMockito.given(jwtService.getSubFromToken(token)).willReturn(Optional.of(userNo));
        BDDMockito.given(userRepository.findByNo(userNo)).willReturn(Optional.of(user));
        //when
        jwtTokenFilter.doFilterInternal(request, response, mockChain);

        //then
        // 다음 필터로 넘어갔는지 검증
        verify(mockChain).doFilter(request, response);
    }

    @Test
    @DisplayName("토큰이 없는 경우 401 에러 응답")
    public void failedInvalidToken() throws Exception{
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain mockChain = mock(FilterChain.class);

        //when
        jwtTokenFilter.doFilterInternal(request, response, mockChain);

        //then
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus(), "401 오류가 뜸");
        verify(mockChain, never()).doFilter(any(), any());
    }

}