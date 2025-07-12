package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.domain.diary.DiaryEntry;
import app.project.jjoojjeollee.domain.diary.DiaryType;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryFindParam;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryData;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntrySortOption;
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
class DiaryEntryRepositoryTest {

    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DiaryEntryRepository diaryEntryRepository;

    @Test
    @DisplayName("일기장 목록 조회(일기 피드 조회)")
    public void successFindByDiaryNo() throws Exception{
        //given
        User user = getTestUser();
        Diary diary = getTestDiary(user);
        DiaryEntry entry1 = DiaryEntry.createDiaryEntry("20250711", "오늘은 저녁을 먹었다.", null, diary, user);
        DiaryEntry entry2 = DiaryEntry.createDiaryEntry("20250712", "오늘도 저녁을 먹었다.", null, diary, user);
        diaryEntryRepository.writeDiaryEntry(entry1);
        diaryEntryRepository.writeDiaryEntry(entry2);

        DiaryEntryFindParam param = new DiaryEntryFindParam();
        param.setDiaryNo(diary.getNo());
        param.setMonth(0);
        param.setPage(30);
        param.setOrderBy(DiaryEntrySortOption.UPDATED_AT);

        //when
        List<DiaryEntryData> result = diaryEntryRepository.findByDiaryNo(param);

        //then
        assertNotNull(result, "결과값이 null이 아니어야 함");
        assertEquals(2, result.size(), "저장한 entry 2개 조회됨");
    }

    @Test
    @DisplayName("일기장 상세 조회")
    public void successFindByEntryNo() throws Exception{
        //given
        User user = getTestUser();
        Diary diary = getTestDiary(user);
        DiaryEntry entry = DiaryEntry.createDiaryEntry("20250711", "오늘은 저녁을 먹었다.", null, diary, user);
        diaryEntryRepository.writeDiaryEntry(entry);

        //when
        DiaryEntry result = diaryEntryRepository.findByEntryNo(entry.getNo());

        //then
        assertEquals(result, entry, "저장된 값이 조회된 결과가 동일함");
        assertEquals(result.getDiary(), diary);
        assertEquals(result.getModificationInfo().getCreatedBy(), user.getNo());
    }
    private Diary getTestDiary(User user) {
        Diary diary = Diary.createDiary("테스트", "ffffff", DiaryType.DEFAULT.toString(), 30, user);
        diaryRepository.save(diary);
        return diary;
    }

    private User getTestUser() {
        User user = User.createUser("test@test.com", "test");
        user.setupUserProfile("테스트","메시지",null);
        userRepository.save(user);
        return user;
    }
}