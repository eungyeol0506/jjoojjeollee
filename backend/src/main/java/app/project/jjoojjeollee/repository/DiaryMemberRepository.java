package app.project.jjoojjeollee.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiaryMemberRepository {

    private final EntityManager em;

    /**
     *  select : 사용자를 기준으로 Diary 조회
     */

    /**
     * select : 다이어리를 기준으로 Member 조회
     */


}
