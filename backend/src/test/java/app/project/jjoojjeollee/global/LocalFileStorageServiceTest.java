package app.project.jjoojjeollee.global;

import app.project.jjoojjeollee.service.LocalFileStorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class LocalFileStorageServiceTest {

    @Autowired
    LocalFileStorageService fileStorageService;

    @DisplayName("프로필 저장 테스트")
    @Test
    public void saveProfileTest() throws Exception{
        //given
        MultipartFile file = createTestMultipartFile();
        Long userNo = 1L;
        FileImageType fileType = FileImageType.PROFILE;

        //when
        String relativePathName = fileStorageService.save(file, fileType, userNo);

        //then
        assertNotNull(relativePathName, "반환값이 있음");
        assertTrue(Files.exists(Paths.get(relativePathName)), "해당 경로로 파일을 만듬");
    }

    @DisplayName("일기 이미지 저장 테스트")
    @Test
    public void saveDiaryImageTest() throws Exception{
        //given
        MultipartFile file = createTestMultipartFile();
        Long diaryEntryNo = 2L;
        FileImageType fileType = FileImageType.DIARY;

        //when
        String relativePathName = fileStorageService.save(file, fileType, diaryEntryNo);

        //then
        assertNotNull(relativePathName, "반환값이 있음");
        assertTrue(Files.exists(Paths.get(relativePathName)), "해당 경로로 파일을 만듬");
        assertTrue(relativePathName.contains(String.valueOf(diaryEntryNo)), "해당 일기번호로 디렉토리를 생성함");
    }

    @DisplayName("이미지 삭제 테스트")
    @Test
    public void deleteFileTest() throws Exception{
        //given
        MultipartFile file = createTestMultipartFile();
        Long diaryEntryNo = 3L;
        FileImageType fileType = FileImageType.DIARY;

        String relativePathName = fileStorageService.save(file, fileType, diaryEntryNo);
        //when
        fileStorageService.delete(relativePathName);
        //then
        assertTrue(Files.notExists(Paths.get(relativePathName)), "해당 경로에 파일 있으면 안됨");
    }

    private MultipartFile createTestMultipartFile() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello".getBytes());
        return file;
    }
}