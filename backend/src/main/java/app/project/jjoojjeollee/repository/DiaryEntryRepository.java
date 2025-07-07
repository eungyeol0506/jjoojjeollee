package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.DiaryEntry;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryFindParam;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryListDto;
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
    public List<DiaryEntryListDto> findByDiaryNo(DiaryEntryFindParam param){
        Diaries d = Diaries.DIARIES.as("d");
        DiaryEntries de = DiaryEntries.DIARY_ENTRIES.as("de");
        Images i = Images.IMAGES.as("i");
        Comments c = Comments.COMMENTS.as("c");

        Table<?> commentCountSubQuery = DSL
                .select(c.ENTRY_NO, DSL.count().as("comment_cnt"))
                .from(c)
                .asTable("cc");
        Long diaryNo = param.getDiaryNo();
        DiaryEntrySortOption orderby = param.getOrderBy();

        return dsl.select(d.DIARY_NO,
                d.NAME,
                d.ANNOUNCEMENT,
                d.VIEW_CNT,
                d.CURRENT_IDX,
                de.ENTRY_NO,
                de.TITLE,
                de.CONTENTS,
                de.CREATED_BY,
                de.CREATED_AT,
                de.UPDATED_AT,
                i.EXTENSION,
                i.STORED_FILE_PATH,
                i.STORED_FILE_NAME,
                        DSL.coalesce(DSL.field("cc.comment_count", Integer.class), 0).as("comment_count")
                )
                .from(d)
                .leftJoin(de).on(d.DIARY_NO.eq(de.DIARY_NO))
                .leftJoin(i).on(de.IMAGE_NO.eq(i.IMAGE_NO))
                .leftJoin(commentCountSubQuery).on(de.ENTRY_NO.eq(DSL.field("cc.entry_no", Long.class)))
                .where(de.DELETED_AT.isNull())
                .and(d.DIARY_NO.eq(diaryNo))
                .orderBy(DSL.field(orderby.getValue(), String.class))
                .fetchInto(DiaryEntryListDto.class);
    }
    /**
     * 일기장 상세 조회
     */
    public DiaryEntry findByEntryNo(Long entryNo, Long userNo){
        return em.createQuery("select de from DiaryEntry de " +
                            "left join fetch de.image " +
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

}
