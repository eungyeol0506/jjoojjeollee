package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.dto.user.UserLoginParam;
import app.project.jjoojjeollee.dto.user.UserProfileSettupParam;
import app.project.jjoojjeollee.dto.user.UserRegisterParam;
import app.project.jjoojjeollee.global.ApiResponse;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
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
            UserRegisterParam param = new UserRegisterParam("test@test.com", "password");

            // when then
            mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(param)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.success").value(true))
                    ;
        }

        @DisplayName("회원가입 실패 - 필드 누락 (@Valid 검증 실패)")
        @Test
        public void failedJoinBlankId() throws Exception {
            // given
            UserRegisterParam param = new UserRegisterParam("test@test.com", "password");

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
            UserRegisterParam param1 = new UserRegisterParam("test@test.com", "password");
            UserRegisterParam param2 = new UserRegisterParam("test@test.com", "password");

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
            UserRegisterParam registerParam = new UserRegisterParam("test@test.com", "password");

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
            UserLoginParam loginParam = new UserLoginParam("test2", "password");

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
            UserRegisterParam registerParam = new UserRegisterParam("test@test.com", "password");

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
            UserRegisterParam registerParam = new UserRegisterParam("test@test.com", "password");

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
//        @DisplayName("로그인 예외 - 탈퇴요청된 회원")
//        @Test
//        public void failedLoginWithdrawnUser() throws Exception{
//
//        }
        // jwt 토큰 발급 실패 케이스
    }

    @Nested
    @DisplayName("프로필 설정 테스트")
    class SetProfile{
        @Test
        @DisplayName("이미지를 수정에 성공하는 경우")
        public void successSetupProfile() throws Exception{
            // given
            long userNo = createTestUser();

            MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
            MockMultipartFile isRemovedImage = new MockMultipartFile("isRemovedImage", "", "application/json", objectMapper.writeValueAsBytes(false));
            MockMultipartFile userProfileParam = new MockMultipartFile("userProfileParam", "", "application/json", objectMapper.writeValueAsBytes(new UserProfileSettupParam("test", "HI")));

            MockHttpSession session = new MockHttpSession();
            session.setAttribute("user", userNo);

            //when //then
            mockMvc.perform(multipart("/api/users/settup")
                            .file(file)
                            .file(userProfileParam)
                            .file(isRemovedImage)
                            .session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("이미지 수정 없이 프로필 수정에 성공하는 경우")
        public void successSetupProfileWithNoImage() throws Exception{
            // given
            long userNo = createTestUser();

            MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
            MockMultipartFile isRemovedImage = new MockMultipartFile("isRemovedImage", "", "application/json", objectMapper.writeValueAsBytes(false));
            MockMultipartFile userProfileParam = new MockMultipartFile("userProfileParam", "", "application/json", objectMapper.writeValueAsBytes(new UserProfileSettupParam("test", "HI")));

            MockHttpSession session = new MockHttpSession();
            session.setAttribute("user", userNo);

            mockMvc.perform(multipart("/api/users/settup")
                            .file(file)
                            .file(userProfileParam)
                            .file(isRemovedImage)
                            .session(session))
                    .andDo(print())
                    .andExpect(status().isOk());

            MockMultipartFile newUserProfileParam = new MockMultipartFile("userProfileParam", "", "application/json", objectMapper.writeValueAsBytes(new UserProfileSettupParam("new_test", "HI")));
            //when //then
            mockMvc.perform(multipart("/api/users/settup")
                            .file(newUserProfileParam)
                            .file(isRemovedImage)
                            .session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("프로필 설정 시 이미지를 삭제하는 경우")
        public void successSetupProfileWithDeleteImage() throws Exception{
// given
            long userNo = createTestUser();

            MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
            MockMultipartFile isRemovedImage = new MockMultipartFile("isRemovedImage", "", "application/json", objectMapper.writeValueAsBytes(false));
            MockMultipartFile userProfileParam = new MockMultipartFile("userProfileParam", "", "application/json", objectMapper.writeValueAsBytes(new UserProfileSettupParam("test", "HI")));

            MockHttpSession session = new MockHttpSession();
            session.setAttribute("user", userNo);

            mockMvc.perform(multipart("/api/users/settup")
                            .file(file)
                            .file(userProfileParam)
                            .file(isRemovedImage)
                            .session(session))
                    .andExpect(status().isOk());

            MockMultipartFile newUserProfileParam = new MockMultipartFile("userProfileParam", "", "application/json", objectMapper.writeValueAsBytes(new UserProfileSettupParam("new_test", "HI")));
            MockMultipartFile newIsRemovedImage = new MockMultipartFile("isRemovedImage", "", "application/json", objectMapper.writeValueAsBytes(true));

            //when //then
            mockMvc.perform(multipart("/api/users/settup")
                            .file(newUserProfileParam)
                            .file(newIsRemovedImage)
                            .session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
        
        @DisplayName("예외 발생 시 DB rollback 테스트")
        @Test
        public void failedCauseException() throws Exception{
            //given

            //when
            //then
        }

        private long createTestUser() throws Exception {
            // user 회원가입 set
            UserRegisterParam registerParam = new UserRegisterParam("test@test.com", "password");
            MvcResult result = mockMvc.perform(post("/api/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerParam)))
                    .andExpect(status().isCreated())
                    .andReturn();

            String StringResponse = result.getResponse().getContentAsString();
            ApiResponse apiResponse = objectMapper.readValue(StringResponse, ApiResponse.class);
            Map<String, Object> data = (Map<String, Object>) apiResponse.getData();
            Integer userNo = (Integer) data.get("userNo");
            long userNoLong = userNo.longValue();
            return userNoLong;
        }
    }

}