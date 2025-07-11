package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.dto.diary.DiaryData;
import app.project.jjoojjeollee.dto.diary.DiaryMemberData;
import app.project.jjoojjeollee.dto.diary.DiarySortOption;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.User;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import target.tables.*;

import java.util.List;

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
     * 일기 정보 조회
     */
    public Diary findByNo(Long no) {
        return em.find(Diary.class, no);
    }

    /**
     * select: 내 프로필에서 일기 목록 조회 메서드
     */
    public List<DiaryData> findMyDiariesByUserNo(Long userNo, String diaryType) {
        Diaries d = Diaries.DIARIES.as("d");
        DiaryMembers dm = DiaryMembers.DIARY_MEMBERS.as("dm");

        return dsl.select(d.DIARY_NO.as("diaryNo"),
                d.NAME,
                d.TYPE,
                d.HEX_COLOR.as("hexColor"),
                d.ANNOUNCEMENT,
                d.BEGIN_AT.as("beginAt"),
                d.END_AT.as("endAt"),
                d.CREATED_BY.as("createdBy"),
                DSL.count(dm.MEMBER_NO)
                        .over().partitionBy(d.DIARY_NO)
                        .as("memberCnt"))
                .from(d)
                .leftJoin(dm).on(dm.DIARY_NO.eq(d.DIARY_NO))
                .where(d.DELETED_AT.isNull(),
                        d.CREATED_BY.eq(userNo),
                        d.TYPE.eq(diaryType))
                .orderBy(d.CREATED_AT.desc())
                .fetchInto(DiaryData.class);
    }

    /**
     * select: 일기 목록 조회 메서드
     */
    public List<DiaryData> findDiariesByUserNo(Long userNo, DiarySortOption sortOption, String keyword) {
        SortField<?> sortField = getSortField(sortOption);

        Diaries d = Diaries.DIARIES.as("d");
        DiaryMembers dm = DiaryMembers.DIARY_MEMBERS.as("dm");

        return dsl.select(d.DIARY_NO.as("diaryNo"),
                        d.NAME,
                        d.TYPE,
                        d.HEX_COLOR.as("hexColor"),
                        d.ANNOUNCEMENT,
                        d.BEGIN_AT.as("beginAt"),
                        d.END_AT.as("endAt"),
                        d.CREATED_BY.as("createdBy"),
                        DSL.count(dm.MEMBER_NO)
                            .over().partitionBy(d.DIARY_NO)
                            .as("memberCnt"))
                .from(d)
                .leftJoin(dm).on(d.DIARY_NO.eq(dm.DIARY_NO))
                .where(d.DELETED_AT.isNull(),
                        keyword != null ? d.NAME.likeIgnoreCase("%"+keyword+"%") : null,
                        dm.USER_NO.eq(userNo))
                .orderBy(sortField)
                .fetchInto(DiaryData.class);

    }

    /**
     * 일기 멤버 정보 조회 메서드
     */
    public List<DiaryMemberData> findDiaryMembers(Long diaryNo){
        Diaries d = Diaries.DIARIES.as("d");
        DiaryMembers dm = DiaryMembers.DIARY_MEMBERS.as("dm");
        UserProfiles up = UserProfiles.USER_PROFILES.as("up");
        Images i = Images.IMAGES.as("i");

        return dsl.select(dm.USER_NO.as("userNo"),
                    dm.IDX,
                    up.NICKNAME,
                    i.STORED_FILE_NAME.as("storedFileName"),
                    i.STORED_FILE_PATH.as("storedFilePath"),
                    i.EXTENSION)
                .from(d)
                .leftJoin(dm).on(dm.DIARY_NO.eq(d.DIARY_NO))
                .leftJoin(up).on(up.USER_NO.eq(dm.USER_NO))
                .leftJoin(i).on(i.IMAGE_NO.eq(up.IMAGE_NO))
                .where(d.DIARY_NO.eq(diaryNo))
                .orderBy(dm.IDX)
                .fetchInto(DiaryMemberData.class);
    }

    private SortField<?> getSortField(DiarySortOption option) {
        Diaries d = Diaries.DIARIES.as("d");
        SortField<?> sortField;
        switch (option) {
            case UPDATED_AT:
                sortField = d.UPDATED_AT.desc();
            case CREATED_AT:
                sortField = d.CREATED_AT.desc();
            case NAME:
                sortField = d.NAME.asc();
            case VIEW_CNT:
                sortField = d.VIEW_CNT.desc();
            default:
                sortField = d.UPDATED_AT.desc();
        }
        return sortField;
    }

    private Table<?> getDiaryMemberCountQuery(DiaryMembers dm, Long diaryNo) {
        return DSL.select(dm.MEMBER_NO, DSL.count().as("member_cnt"))
                .from(dm)
                .where(dm.DIARY_NO.eq(diaryNo))
                .asTable("sub_dm");
    }
}
