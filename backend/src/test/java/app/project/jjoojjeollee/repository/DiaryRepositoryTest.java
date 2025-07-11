package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.domain.user.User;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DiaryRepositoryTest {
    @Autowired
    DSLContext dsl;

    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("일기 생성 테스트 - 성공 case")
    public void save() throws Exception{
        //given
        User user = User.createUser("test@test.com", "test");
        Long userNo = userRepository.save(user);
        Diary diary = Diary.createDiary("test", "ffffff", "default", 30, user);

        //when
        diaryRepository.save(diary);

        //then
    }

    @Test
    @DisplayName("내 프로필에서 일기 목록 조회 메서드")
    public void findByUserNo() throws Exception{
        //given
        Long userNo = 1L;

        //when

        //then
    }

    @Test
    @DisplayName("일기 목록 조회 메서드")
    public void findDiaryListByUserNoTest() throws Exception{

    }
}