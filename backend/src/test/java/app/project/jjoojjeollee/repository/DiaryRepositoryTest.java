package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.domain.diary.DiaryType;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.dto.diary.DiaryData;
import app.project.jjoojjeollee.dto.diary.DiaryMemberData;
import app.project.jjoojjeollee.dto.diary.DiarySortOption;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
    @DisplayName("일기 정보 기본 조회")
    public void successFindByNo() throws Exception{
        //given
        User user = getTestUser();
        Diary diary = getTestDiary(user, DiaryType.DEFAULT.toString());

        //when
        Diary result = diaryRepository.findByNo(diary.getNo());

        //then
        Assertions.assertThat(result).isEqualTo(diary);
    }

    @Test
    @DisplayName("내 프로필에서 일기 목록 조회")
    public void successFindMyDiariesByUserNo() throws Exception{
        //given
        User user = getTestUser();
        Diary diary = getTestDiary(user,DiaryType.SHARED.toString());

        //when
        List<DiaryData> result1 = diaryRepository.findMyDiariesByUserNo(user.getNo(), DiaryType.SHARED.toString());
        List<DiaryData> result2 = diaryRepository.findMyDiariesByUserNo(user.getNo(), DiaryType.DEFAULT.toString());

        //then
        Assertions.assertThat(result1.size()).isEqualTo(1).as("추가한 Shared 다이어리를 조회할 수 있음");
        Assertions.assertThat(result1.get(0).getDiaryNo()).isEqualTo(diary.getNo()).as("추가한 다이어리 번호가 조회된 값과 동일");

        Assertions.assertThat(result2.size()).isEqualTo(0).as("Default 다이어리는 추가하지 않아 값이 없음");

    }

    @Test
    @DisplayName("홈에서 일기 목록 조회")
    public void successFindDiariesByUserNo() throws Exception{
        //given
        User user1 = getTestUser();
        Diary diaryByUser1 = getTestDiary(user1, DiaryType.DEFAULT.toString());

        User user2 = getTestUser();
        Diary diaryByUser2 = Diary.createDiary("test", "ffffff", DiaryType.DEFAULT.toString(), 30, user2);
        diaryByUser2.addMember(user1, 1);
        diaryRepository.save(diaryByUser2);

        //when
        List<DiaryData> result1 = diaryRepository.findDiariesByUserNo(user1.getNo(), DiarySortOption.UPDATED_AT, null);
        List<DiaryData> result2 = diaryRepository.findDiariesByUserNo(user2.getNo(), DiarySortOption.UPDATED_AT, null);

        //then
        Assertions.assertThat(result1.size()).isEqualTo(2).as("멤버로 추가되어서 두개의 다이어리가 확인되어야 함");
        Assertions.assertThat(result2.size()).isEqualTo(1).as("한개의 다이어리가 확인되어야 함");

    }

    @Test
    @DisplayName("일기 멤버 정보 조회")
    public void successFindDiaryMembers() throws Exception{
        //given
        User user1 = getTestUser();
        User user2 = getTestUser();
        Diary diaryByUser2 = Diary.createDiary("test", "ffffff", DiaryType.DEFAULT.toString(), 30, user2);
        diaryByUser2.addMember(user1, 1);

        diaryRepository.save(diaryByUser2);
        //when
        List<DiaryMemberData> result = diaryRepository.findDiaryMembers(diaryByUser2.getNo());
        //then
        Assertions.assertThat(result.size()).isEqualTo(2).as("두 명의 멤버가 존재해야함");
    }


    private Diary getTestDiary(User user, String type) {
        Diary diary = Diary.createDiary("test", "ffffff", type, 30, user);
        diaryRepository.save(diary);
        return diary;
    }

    private User getTestUser() {
        User user = User.createUser("test@test.com", "test");
        userRepository.save(user);
        user.setupUserProfile("테스트닉네임", "메시지", null);

        return user;
    }
}