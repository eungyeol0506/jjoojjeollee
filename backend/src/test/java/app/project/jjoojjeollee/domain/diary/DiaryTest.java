package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiaryTest {

    @Test
    @DisplayName("일기 생성 테스트")
    public void createDiaryTest() throws Exception{
        //given
        String name = "TEST";
        String hexColor = "ff0000";
        String type = "default";
        int dDay = 30;

        User user = User.createUser("test.email.com", "test");

        //when
        Diary result = Diary.createDiary(name, hexColor, type, dDay, user);

        //then
        assertNotNull(result, "일기 정보가 생성됨");
        assertNotNull(result.getDiaryMembers(), "일기에 멤버가 추가됨");
    }
    
    @Test
    public void setupDiaryTest() throws Exception{
        //given
        Diary diary = getTestDiaryData();

        //when
        diary.setupDiary("new Name","ff0000");
        diary.changeAnnouncement("공지사항");

        //then
        assertEquals(diary.getName(), "new Name", "표지 정보가 수정됨");
        assertEquals(diary.getAnnouncement(), "공지사항", "공지사항 정보 수정됨");
        assertEquals(diary.getModificationInfo().getUpdatedBy(), 0L, "업데이트 정보 저장됨");
        assertEquals(diary.getModificationInfo().getUpdatedAt().getDayOfMonth(), LocalDate.now().getDayOfMonth(), "업데이트 정보 저장됨");

    }

    @Test
    public void removeMemberTest() throws Exception{
        //given
        Diary diary = getTestDiaryData();
        User oldUser = diary.getDiaryMembers().get(0).getMember();
        User newUser = User.createUser("test2.email.com", "test2");

        diary.addMember(newUser, 1);
        //when
        diary.removeMember(oldUser);
        //then
        assertEquals(diary.getDiaryMembers().size(), 1, "한 명의 멤버만 남아야 함");
        assertEquals(diary.getDiaryMembers().get(0).getIdx(), 0,"인덱스는 0이어야 함");
    }
    
    @Test
    @DisplayName("조회수 갱신 테스트")
    public void updateViewCntTest() throws Exception{
        //given
        Diary diary = getTestDiaryData();

        int oldCnt = diary.getViewCnt();
        //when
        diary.updateViewCnt();
        //then
        assertEquals(diary.getViewCnt(), oldCnt+1, "조회수가 1 증가함");
    }

    @Test
    public void nextIdxTest() throws Exception{
        //given
        Diary diary = getTestDiaryData();
        diary.addMember(User.createUser("2test@test.com", "tt"), 1);

        //when
        diary.nextIdx();
        diary.nextIdx();

        //then
        assertEquals(diary.getCurrentIndex(), 0, "인덱스는 인원수에 맞춰 사이클로 돌아야함");
    }

    @Test
    public void deleteDiaryTest() throws Exception{
        //given
        Diary diary = getTestDiaryData();

        //when
        diary.deleteDiary(0L);
        
        //then
        assertNotNull(diary.getModificationInfo().getDeletedAt(), "삭제일자 정보가 존재함");
    }

    private Diary getTestDiaryData() {
        String name = "TEST";
        String hexColor = "ff0000";
        String type = "default";
        int dDay = 30;
        User user = User.createUser("test.email.com", "test");

        return Diary.createDiary(name, hexColor, type, dDay, user);
    }

}