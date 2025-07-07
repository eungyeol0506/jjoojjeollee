package app.project.jjoojjeollee.service.diary;


import app.project.jjoojjeollee.domain.diary.DiaryEntry;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryFindParam;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryListDto;
import app.project.jjoojjeollee.dto.diaryentry.DiaryEntryWriteParam;
import app.project.jjoojjeollee.repository.DiaryEntryRepository;
import app.project.jjoojjeollee.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryEntryRepository diaryEntryRepository;

    /**
     * 일기장 목록 조회
     */
    public List<DiaryEntryListDto> findDiaryEntriesByParam(DiaryEntryFindParam param){
        return diaryEntryRepository.findByDiaryNo(param);
    }
    /**
     * 상세 조회
     */
    public DiaryEntry findDiaryEntryByNo(Long entryNo, Long userNo) {
        return diaryEntryRepository.findByEntryNo(entryNo, userNo);
    }

    /**
     * 작성
     */
    @Transactional
    public Long writeDiaryEntry(DiaryEntryWriteParam param){
        return null;
    }
}
