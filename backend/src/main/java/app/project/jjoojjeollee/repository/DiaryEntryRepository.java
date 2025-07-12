package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.DiaryEntry;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryFindParam;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryData;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntrySortOption;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import target.tables.Comments;
import target.tables.Diaries;
import target.tables.DiaryEntries;
import target.tables.Images;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryEntryRepository {

    private final EntityManager em;
    private final DSLContext dsl;

    /**
     * 일기장 목록 조회
     */
    public List<DiaryEntryData> findByDiaryNo(DiaryEntryFindParam param){
        Diaries d = Diaries.DIARIES.as("d");
        DiaryEntries de = DiaryEntries.DIARY_ENTRIES.as("de");
        Images i = Images.IMAGES.as("i");
        Comments c = Comments.COMMENTS.as("c");

        Long diaryNo = param.getDiaryNo();
        DiaryEntrySortOption orderby = param.getOrderBy();

        return dsl.select(d.DIARY_NO.as("diaryNo"),
                        d.NAME,
                        d.ANNOUNCEMENT,
                        d.VIEW_CNT.as("viewCnt"),
                        d.CURRENT_IDX.as("currentIdx"),
                        de.ENTRY_NO.as("entryNo"),
                        de.TITLE,
                        de.CONTENTS,
                        de.WRITER_NICKNAME.as("writerNickname"),
                        de.CREATED_BY.as("createdBy"),
                        de.CREATED_AT.as("createdAt"),
                        de.UPDATED_AT.as("updatedAt"),
                        i.STORED_FILE_PATH.as("storedFilePath"),
                        i.STORED_FILE_NAME.as("storedFileName"),
                        i.EXTENSION,
                        DSL.count(c.COMMENT_NO)
                            .over().partitionBy(d.DIARY_NO)
                            .as("commentCnt"))
//                DSL.coalesce(DSL.field("cc.comment_count", Integer.class), 0).as("comment_count")
//                )
                .from(d)
                .leftJoin(de).on(de.DIARY_NO.eq(d.DIARY_NO))
                .leftJoin(i).on(i.IMAGE_NO.eq(de.IMAGE_NO))
                .leftJoin(c).on(c.ENTRY_NO.eq(de.ENTRY_NO))
                .where(de.DELETED_AT.isNull(),
                        d.DIARY_NO.eq(diaryNo))
                .orderBy(DSL.field(orderby.getValue(), String.class))
                .fetchInto(DiaryEntryData.class);
    }
    /**
     * 일기장 상세 조회
     */
    public DiaryEntry findByEntryNo(Long entryNo){
        return em.createQuery("select de from DiaryEntry de " +
                            "left join fetch de.image " +
                            "left join fetch de.diary " +
                            "where de.no = :entryNo", DiaryEntry.class)
                .setParameter("entryNo", entryNo)
                .getSingleResult();
    }
//    public List<Integer> getMonths(){
//
//    }

    /**
     * 작성 메서드
     */
    public Long writeDiaryEntry(DiaryEntry diaryEntry){
        if (diaryEntry.getNo() != null) {
            throw new IllegalStateException("Diary Entry already exists");
        }

        em.persist(diaryEntry);
        return diaryEntry.getNo();
    }
}
