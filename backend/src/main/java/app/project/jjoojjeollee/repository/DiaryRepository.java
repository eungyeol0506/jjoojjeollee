package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.dto.diary.DiaryDetailData;
import app.project.jjoojjeollee.dto.diary.DiaryEntryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import static org.jooq.impl.DSL.*;
import static target.tables.Diaries.DIARIES;
import static target.tables.DiaryEntries.DIARY_ENTRIES;
import static target.tables.Images.IMAGES;
import static target.tables.Comments.COMMENTS;

@Repository
@RequiredArgsConstructor
public class DiaryRepository {

    private final EntityManager em;
    private final DSLContext dsl;

    /**
     * create: diary
     */
    public Long save(Diary diary) {
        if (diary.getNo() != null) {
            throw new IllegalStateException("Diary already exists");
        }

        em.persist(diary);
        return diary.getNo();
    }

    /**
     * select: 상세보기
     */
    public DiaryDetailData findByNo(Long no) {
        return dsl.select(
                                    DIARIES.DIARY_NO,
                                    DIARIES.NAME,
                                    DIARIES.HEX_COLOR,
                                    multiset(
                                        select(DIARY_ENTRIES.ENTRY_NO,
                                                DIARY_ENTRIES.TITLE,
                                                DIARY_ENTRIES.CONTENTS,
                                                DIARY_ENTRIES.WRITER_NAME,
                                                DIARY_ENTRIES.CREATED_BY,
                                                IMAGES.IMAGE_NO,
                                                IMAGES.STORED_FILE_PATH,
                                                IMAGES.STORED_FILE_NAME,
                                                IMAGES.EXTENSION
                                                )
                                            .from(DIARY_ENTRIES)
                                            .leftJoin(IMAGES).on(IMAGES.IMAGE_NO.eq(DIARY_ENTRIES.IMAGE_NO))
//                                            .leftJoin(COMMENTS).on(COMMENTS.ENTRY_NO.eq(DIARY_ENTRIES.ENTRY_NO))
                                            .where(DIARY_ENTRIES.DIARY_NO.eq(DIARIES.DIARY_NO))

                                            .orderBy(DIARY_ENTRIES.CREATED_AT)
                                        ).as("diaryEntries")
                                            .convertFrom(r -> r.into(DiaryEntryDto.class))
                                    )
                                    .from(DIARIES)
                                    .where(DIARIES.DIARY_NO.eq(no))
                                    .fetchOneInto(DiaryDetailData.class);
    }
}
