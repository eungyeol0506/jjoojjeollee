package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.DiaryType;
import app.project.jjoojjeollee.dto.diary.DiaryDetailData;
import app.project.jjoojjeollee.dto.diary.DiaryEntryDto;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static target.tables.Diaries.DIARIES;
import static target.tables.DiaryEntries.DIARY_ENTRIES;
import static target.tables.Images.IMAGES;
import static target.tables.Comments.COMMENTS;
import static target.enums.DiariesType.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DiaryRepositoryTest {
    @Autowired
    DSLContext dsl;

    @Autowired
    DiaryRepository diaryRepository;

    @BeforeEach
    void setup() {
        // 테스트용 diary, diary_entries, images, comments 삽입
        dsl.insertInto(DIARIES)
                .set(DIARIES.NAME, "테스트 다이어리")
                .set(DIARIES.TYPE, DEFAULT)
                .set(DIARIES.HEX_COLOR, "#FFFFFF")
                .set(DIARIES.CURRENT_INDEX, 1)
                .set(DIARIES.CREATED_AT, LocalDateTime.now())
                .set(DIARIES.BEGIN_AT, LocalDate.now())
                .set(DIARIES.END_AT, LocalDate.now())
                .set(DIARIES.CREATED_BY, 1L)
                .execute();

        dsl.insertInto(DIARY_ENTRIES)
                .set(DIARY_ENTRIES.DIARY_NO, 1L)
                .set(DIARY_ENTRIES.IMAGE_NO, 1L)
                .set(DIARY_ENTRIES.TITLE, "제목")
                .set(DIARY_ENTRIES.CONTENTS, "일기 내용")
                .set(DIARY_ENTRIES.WRITER_NAME, "쭈쩌리")
                .set(DIARY_ENTRIES.CREATED_AT, LocalDateTime.now())
                .set(DIARY_ENTRIES.CREATED_BY, 1L)
                .execute();

        dsl.insertInto(IMAGES)
                .set(IMAGES.STORED_FILE_NAME, "image")
                .set(IMAGES.STORED_FILE_PATH, "test")
                .set(IMAGES.EXTENSION, "png")
                .set(IMAGES.ORIGINAL_FILE_NAME, "fakePath://image.png")
                .execute();

        dsl.insertInto(COMMENTS)
                .set(COMMENTS.ENTRY_NO, 1L)
                .set(COMMENTS.COMMENT_TEXT, "테스트 텍스트")
                .set(COMMENTS.CREATED_AT, LocalDateTime.now())
                .set(COMMENTS.CREATED_BY, 1L)
                .execute();
    }

    @Test
    @DisplayName("findByNo: 상세 조회 시 MULTISET 결과 포함")
    void testFindByNo() {
        // when
        DiaryDetailData result = diaryRepository.findByNo(1L);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo("테스트 다이어리");

        List<DiaryEntryDto> entries = result.getDiaryEntries();
        Assertions.assertThat(entries).hasSize(1);

        DiaryEntryDto entry = entries.get(0);
        Assertions.assertThat(entry.getContents()).isEqualTo("일기 내용");
        Assertions.assertThat(entry.getStoredFileName()).isEqualTo("image.jpg");
        Assertions.assertThat(entry.getCommentCount()).isEqualTo(1);
    }

}