package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.dto.diary.DiaryListDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import target.tables.Diaries;
import target.tables.DiaryMembers;

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
     * select: 내 프로필에서 일기 목록 조회 메서드
     */
    public List<Diary> findDiariesByUserNo(Long userNo) {
        return em.createQuery("select d from Diary d " +
                        "left join fetch d.diaryMembers dm " +
                        "left join fetch dm.member m " +
                        "where d.modificationInfo.createdBy = :userNo" +
                        " and d.modificationInfo.deletedAt is null", Diary.class)
                .setParameter("userNo", userNo)
                .getResultStream()
                .toList();
    }

    public List<Diary> findSharedDiariesByUserNo(Long userNo) {
        return em.createQuery("select d from Diary d " +
                        "left join fetch d.diaryMembers dm " +
                        "left join fetch dm.member m " +
                        "where dm.member.no = :userNo" +
                        " and d.modificationInfo.createdBy <> :userNo" +
                        " and d.modificationInfo.deletedAt is null", Diary.class)
                .setParameter("userNo", userNo)
                .getResultStream()
                .toList();
    }
    /**
     * select: 일기 목록 조회 메서드
     */
    public List<DiaryListDTO> findDiaryListByUserNo(Long userNo, DiarySortOption sortOption) {
        SortField<?> sortField = getSortField(sortOption);

        Diaries d = Diaries.DIARIES.as("d");
        DiaryMembers dm = DiaryMembers.DIARY_MEMBERS.as("dm");

        return dsl.select(
                d.DIARY_NO,
                d.NAME,
                d.HEX_COLOR,
                d.ANNOUNCEMENT,
                d.TYPE,
                d.END_AT
                /*함께하는 멤버 수*/
            )
                .from(d)
                .leftJoin(dm).on(d.DIARY_NO.eq(dm.DIARY_NO))
                .where(d.DELETED_AT.isNull())
                .orderBy(sortField)
                .limit(30)
                .fetchInto(DiaryListDTO.class);

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
}
