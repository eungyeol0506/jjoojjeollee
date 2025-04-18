package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UserRepository.class})
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("사용자 저장 및 조회")
    void save() {
        User user = User.createUser("testId", "testPw", "test@test.com");

        Long no = userRepository.save(user);
        User findUser = userRepository.findByNo(no);

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
}