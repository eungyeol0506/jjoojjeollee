package app.project.jjoojjeollee.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiaryEntryRepository {

    private final EntityManager em;


}
