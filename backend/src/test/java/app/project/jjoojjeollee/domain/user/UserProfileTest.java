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
        String nickname = "테스트";
        String message = null;
        Image profileImage = null;

        //when
        UserProfile userProfile = UserProfile.createUserProfile(nickname,message,profileImage);

        //then
        assertNotNull(userProfile);
        assertNull(userProfile.getProfileImage());
    }

}