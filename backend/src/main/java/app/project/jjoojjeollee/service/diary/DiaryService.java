package app.project.jjoojjeollee.service.diary;


import app.project.jjoojjeollee.domain.common.Image;
import app.project.jjoojjeollee.domain.diary.Diary;
import app.project.jjoojjeollee.domain.diary.DiaryEntry;
import app.project.jjoojjeollee.domain.diary.DiaryMember;
import app.project.jjoojjeollee.domain.diary.DiaryType;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.dto.diary.*;
import app.project.jjoojjeollee.dto.diaryentry.*;
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
     * 일기 생성
     */
    public Long createDiary(DiaryCreateParam param, User creator) {
        Diary diary = Diary.createDiary(param.getName(),
                param.getHexColor(),
                param.getType(),
                param.getDDay(),
                creator);

        diaryRepository.save(diary);
        return diary.getNo();
    }

    /**
     * 일기 표지 정보
     */
    public DiaryData getDiaryInfo(Long diaryNo){
        Diary diary = diaryRepository.findByNo(diaryNo);
        return DiaryData.from(diary);
    }

    /**
     * 일기 표지 수정
     */
    public void setupDiary(DiarySetupParam param){
        Diary diary = diaryRepository.findByNo(param.getDiaryNo());

        diary.setupDiary(param.getName(), param.getHexColor());
    }
    /**
     * 공지사항 수정
     */
    public void updateDiaryAnnouncement(Long diaryNo, String announcement) {
        Diary diary = diaryRepository.findByNo(diaryNo);
        diary.changeAnnouncement(announcement);
    }

    /**
     * 내 프로필에서 일기 목록 조회 (기본&공유)
     */
    public List<DiaryData> findMyDiaries(User user, DiaryType diaryType) {
        return diaryRepository.findMyDiariesByUserNo(user.getNo(), diaryType.toString());
    }

    /**
     * 홈에서 일기 목록 조회
     */
    public List<DiaryData> findDiaries(User user, DiarySortOption sortOption, String keyword) {
        return diaryRepository.findDiariesByUserNo(user.getNo(), sortOption, keyword);
    }

    /**
     * 일기 멤버 목록
     */
    public List<DiaryMemberData> findMembers(Long diaryNo){
        return diaryRepository.findDiaryMembers(diaryNo);
    }
    /**
     * 일기 나가기
     */
    public void exitDiary(Long diaryNo, User user) {
        Diary diary = diaryRepository.findByNo(diaryNo);
        diary.removeMember(user);
    }

    // ----------
    /**
     * 일기장 목록 조회
     */
    public List<DiaryEntryListDto> findDiaryEntriesByParam(DiaryEntryFindParam param){
        return diaryEntryRepository.findByDiaryNo(param);
    }
    /**
     * 상세 조회
     */
    public DiaryEntry findDiaryEntryByNo(Long entryNo) {
        DiaryEntry entry = diaryEntryRepository.findByEntryNo(entryNo);
        entry.getDiary().updateViewCnt();

        return entry;
    }

    /**
     * 일기내용 작성
     */
    @Transactional
    public Long writeDiaryEntry(DiaryEntryWriteParam param, User author, Image image) {
        Diary diary = diaryRepository.findByNo(param.getDiaryNo());

        DiaryEntry entry = DiaryEntry.createDiaryEntry(param.getEntryContent().getTitle(),
                                        param.getEntryContent().getContents(),
                                        image,
                                        diary,
                                        author);
        diaryEntryRepository.writeDiaryEntry(entry);
        if(diary.getType().equals(DiaryType.SHARED)){
            diary.nextIdx();
        }

        return entry.getNo();
    }

    /**
     * 일기내용 수정
     */
    public void updateDiaryEntry(DiaryEntryUpdateParam param, Image image) {
        DiaryEntry entry = diaryEntryRepository.findByEntryNo(param.getEntryNo());
        entry.editDiaryEntry(param.getEntryContent().getTitle(),
                param.getEntryContent().getContents(),
                image);
    }

    /**
     * 일기내용 삭제
     */
    public void deleteDiaryEntry(Long entryNo) {
        DiaryEntry entry = diaryEntryRepository.findByEntryNo(entryNo);
        entry.deleteDiaryEntry();
    }
}
