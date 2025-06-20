package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.dto.diary.DiaryListDTO;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        Diary result = diaryRepository.findDiariesByUserNo(userNo).get(0);
        assertNotNull(result);
        assertNotNull(result.getDiaryMembers());
    }

    @Test
    @DisplayName("내 프로필에서 일기 목록 조회 메서드")
    public void findByUserNo() throws Exception{
        //given
        Long userNo = 1L;

        //when
        List<Diary> result = diaryRepository.findDiariesByUserNo(userNo);
        List<Diary> result2 = diaryRepository.findSharedDiariesByUserNo(userNo);

        //then
        assertNotNull(result);
        assertNotNull(result2);
    }

    @Test
    @DisplayName("일기 목록 조회 메서드")
    public void findDiaryListByUserNoTest() throws Exception{
        //given
        //when
        List<DiaryListDTO> diaries = diaryRepository.findDiaryListByUserNo(1L, DiarySortOption.UPDATED_AT);

        //then
        assertNotNull(diaries);
    }
}