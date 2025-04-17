package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UserRepository.class})
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("새로운 사용자 저장(persist) 성공")
    void save() {
        User user = User.createUser("test", "test", "test@test.com");

        Long id = userRepository.save(user);

        User findUser = userRepository.findByNo(id);
        assertEquals(user, findUser, "동일한 객체");
        Assertions.assertThat(id).isNotNull();
    }
}