package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.dto.diary.DiaryDetailData;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class DiaryRepository {

    private final EntityManager em;
    private final DSLContext dsl;

    /**
     * create: diary
     */
    public Long save(Diary diary) {
        if(diary.getNo() != null){
            throw new IllegalStateException("Diary already exists");
        }

        em.persist(diary);
        return diary.getNo();
    }

    /**
     * select: 상세보기
     */
    public Diary findByNo(Long no) {

    }
}
