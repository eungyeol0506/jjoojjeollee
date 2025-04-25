package app.project.jjoojjeollee.domain.user;

import app.project.jjoojjeollee.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserProfileTest {
    @Test
    @DisplayName("UserProfile 생성 메서드 테스트")
    public void createUserProfile() throws Exception{
        //given
        String nickname = "nickname";
        String message = "message";
        Image profileImage = Image.createImage("fileName.png", "0101", "resources/", "png");

        User testUser = User.createUser("id", "pw", "email@email.com");

        //when
        testUser.setupUserProfile(nickname, message, profileImage);

        //then
        assertNotNull(testUser.getProfile(), "프로필 엔티티 값이 있음");
        assertEquals(nickname, testUser.getProfile().getNickname(), "설정한 닉네임 값을 갖고 있음");
        assertEquals(message, testUser.getProfile().getLineMessage(), "설정한 한줄메시지 값을 갖고 있음");
        assertEquals(profileImage, testUser.getProfile().getProfileImage(), "설정한 프로필 값을 갖고있음");
    }
    
    @Test
    @DisplayName("UserProfile 수정 메서드 테스트 - 이미지 기존 값 유지")
    public void updateUserProfile() throws Exception{
        //given
        String nickname = "test";
        String lineMessage = "Domain method test";
        Image profileImage = Image.createImage("fileName.png", "0101", "resources/", "png");

        User testUser = User.createUser("id", "pw", "test@email.com");
        testUser.setupUserProfile(nickname, lineMessage, profileImage);

        //when
        testUser.setupUserProfile("new nickname", "new message", null);

        //then
        assertNotNull(testUser.getProfile().getProfileImage(), "기존 이미지 값 있음");
        assertEquals("new nickname", testUser.getProfile().getNickname(), "설정한 닉네임 값을 갖고 있음");
        assertEquals("new message", testUser.getProfile().getLineMessage(), "설정한 한줄메시지 값을 갖고 있음");
        assertEquals(profileImage, testUser.getProfile().getProfileImage(), "설정한 프로필 값을 갖고있음");
    }
    
    @Test
    @DisplayName("UserProfile 이미지 삭제 메서드 테스트")
    public void removeUserProfileImage() throws Exception{
        //given
        User testUser = User.createUser("id", "pw", "test@email.com");

        Image profileImage = Image.createImage("fileName.png", "0101", "/resources", "png");
        testUser.setupUserProfile("nickname","lineMessage",profileImage);

        //when
        testUser.getProfile().removeUserProfileImage();
        
        //then
        assertNull(testUser.getProfile().getProfileImage(), "프로필이미지 값이 널이어야 함");
    }
}