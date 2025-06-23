package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.common.Image;
import app.project.jjoojjeollee.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class DiaryEntryTest {

    @Test
    @DisplayName("일기 작성 메서드 테스트")
    public void createDiaryEntryTest() throws Exception{
        //given
        String title = "2025년 06월 23일";
        String contents = "테스트 일기 글";
        Image image = Image.createImage("test.png", "12345!@#$%.png", "fakePath://","png");

        User author = User.createUser("test", "test");
        author.setupUserProfile("nickname", "안녕하세요", image);

        Diary diary = Diary.createDiary("1","2","3", 4, author);

        //when
        DiaryEntry diaryEntry = DiaryEntry.createDiaryEntry(title, contents, null, diary, author);

        //then
        assertNotNull(diaryEntry);
    }

    @Test
    @DisplayName("일기 수정 메서드 테스트")
    public void editDiaryEntryTest() throws Exception{
        //given
        String title = "2025년 06월 23일";
        String contents = "테스트 일기 글";
        Image image = Image.createImage("test.png", "12345!@#$%.png", "fakePath://","png");
        Image entryImage = Image.createImage("12345.png", "12345!@#$%.png", "fakePath://","png");

        User author = User.createUser("test", "test");
        author.setupUserProfile("nickname", "안녕하세요", image);

        Diary diary = Diary.createDiary("1","2","3", 4, author);
        DiaryEntry diaryEntry = DiaryEntry.createDiaryEntry(title, contents, null, diary, author);

        //when
        diaryEntry.editDiaryEntry(title, contents, entryImage);

        //then
        assertNotNull(diaryEntry);
        assertNotNull(diaryEntry.getImage());
    }
}