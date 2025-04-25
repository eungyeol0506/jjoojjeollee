package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("사용자 저장 및 조회")
    void save() {
        User user = User.createUser("testId", "testPw", "test@test.com");

        Long no = userRepository.save(user);
        User findUser = userRepository.findByNo(no).get();

        assertEquals(user, findUser, "저장하고 사용자와 조회한 사용자가 동일함");
    }

    @DisplayName("이메일로 사용자 조회")
    @Test
    void findByEmail(){
        User user = User.createUser("testId", "testPw", "test@test.com");
        userRepository.save(user);

        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        Assertions.assertThat(findUser).isPresent();
    }

    @DisplayName("유저프로필 생성 후 조회")
    @Test
    public void settupProfile() throws Exception{
        //given
        User user = User.createUser("testId", "testPw", "test@test.com");
        Long userNo = userRepository.save(user);

        user.setupUserProfile("테스트", null, null);
        userRepository.save(user);
        
        //when
        User findUser = userRepository.findWithProfileByNo(userNo).orElseThrow(
                () -> new IllegalStateException("값을 불러오는 데 실패 (왜?)")
        );
        
        //then
        assertNotNull(findUser, "save한 유저를 찾아옴");
        assertNotNull(findUser.getProfile(), "프로필 객체 있음");
        assertNull(findUser.getProfile().getProfileImage(), "근데 이미지는 널값임");
    }
    
    @DisplayName("유저프로필 생성되지 않으면 프로필 조회 불가능")
    @Test
    public void failedFindWithProfile() throws Exception{
        //given
        User user = User.createUser("testId", "testPw", "test@test.com");
        userRepository.save(user);

        //when
        Optional<User> findUser = userRepository.findWithProfileByNo(user.getNo());

        //then
        assertTrue(findUser.isEmpty(), "유저값이 persist되어도 프로필이 없어서 찾아오지 못함");
    }
}