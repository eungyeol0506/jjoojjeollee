package app.project.jjoojjeollee.service.diary;


import app.project.jjoojjeollee.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    /**
     * 일기장 생성 메서드
     */

}
