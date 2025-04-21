package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.param.user.UserLoginParam;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class UserApiTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper; // JSON 변환용 (임시)

    @Nested
    @DisplayName("회원가입 요청 테스트")
    class Join{

        @DisplayName("회원가입 성공")
        @Test
        public void successJoin() throws Exception {
            // given
            UserRegisterParam param = new UserRegisterParam("test", "password", "test@test.com");

            // when then
            mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(param)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNumber())
                    ;
        }

        @DisplayName("회원가입 실패 - 필드 누락 (@Valid 검증 실패)")
        @Test
        public void failedJoinBlankId() throws Exception {
            // given
            UserRegisterParam param = new UserRegisterParam("", "password", "test@test.com");

            // when then
            mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(param)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.error").isNotEmpty())
            ;
        }

        @DisplayName("회원가입 실패 - 이메일중복 (@Valid 검증 실패)")
        @Test
        public void failedJoinDuplicatedEmail() throws Exception{
            //given
            UserRegisterParam param1 = new UserRegisterParam("test1", "password", "test@test.com");
            UserRegisterParam param2 = new UserRegisterParam("test2", "password", "test@test.com");

            mockMvc.perform(post("/api/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param1)));

            // when then
            mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(param2)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.error").isNotEmpty())
            ;
        }
    }

    @Nested
    @DisplayName("로그인 테스트")
    class Login{
        @DisplayName("로그인 성공")
        @Test
        public void successLogin() throws Exception{
            //given
            UserRegisterParam registerParam = new UserRegisterParam("test", "password", "test@test.com");

            mockMvc.perform(post("/api/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerParam)));

            UserLoginParam loginParam = new UserLoginParam("test", "password");
            //when then
            mockMvc.perform(post("/api/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginParam)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true));
        }

        @DisplayName("로그인 실패 - 존재하지 않는 사용자")
        @Test
        public void failedLoginUserNotFound() throws Exception{
            //given
            UserLoginParam loginParam = new UserLoginParam("test", "password");

            //when then
            mockMvc.perform(post("/api/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginParam)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.error").isNotEmpty());
        }

        @DisplayName("로그인 실패 - 공백 (@Valid 검증 실패)")
        @Test
        public void failedLoginBlankEmail() throws Exception{
            //given
            UserRegisterParam registerParam = new UserRegisterParam("test", "password", "test@test.com");

            mockMvc.perform(post("/api/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerParam)));

            UserLoginParam loginParam = new UserLoginParam("", "password"); // 아이디가 공백

            //when then
            mockMvc.perform(post("/api/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginParam)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.error").isNotEmpty());
        }
        @DisplayName("로그인 실패 - 비밀번호 불일치")
        @Test
        public void failedLoginMismatchPassword() throws Exception{
            //given
            UserRegisterParam registerParam = new UserRegisterParam("test", "password", "test@test.com");

            mockMvc.perform(post("/api/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerParam)));

            UserLoginParam loginParam = new UserLoginParam("test@test.com", "not_password");

            //when then
            mockMvc.perform(post("/api/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginParam)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.error").isNotEmpty());
        }
        @DisplayName("로그인 예외 - 탈퇴요청된 회원")
        @Test
        public void failedLoginWithdrawnUser() throws Exception{
            //given
            UserRegisterParam registerParam = new UserRegisterParam("test", "password", "test@test.com");

            MvcResult result = mockMvc.perform(post("/api/users/signup")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(registerParam)))
                                        .andReturn();
            String response = result.getResponse().getContentAsString();

            JsonNode node = objectMapper.readTree(response);
            Long userNo = node.get("data").asLong();

            MockHttpSession session = new MockHttpSession();
            session.setAttribute("user", userNo);

            mockMvc.perform(delete("/api/users/delete")
                            .session(session));

            UserLoginParam loginParam = new UserLoginParam("test@test.com", "password");

            //when then
            mockMvc.perform(post("/api/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginParam)))
                    .andDo(print())
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.error").isNotEmpty());
        }
        // jwt 토큰 발급 실패 케이스
    }


}