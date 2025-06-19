package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

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
}
