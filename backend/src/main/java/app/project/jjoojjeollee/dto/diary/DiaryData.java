package app.project.jjoojjeollee.dto.diary;

import app.project.jjoojjeollee.domain.diary.Diary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DiaryData {
    private Long diaryNo;
    private String name;
    private String type;
    private String hexColor;
    private String announcement;
    private LocalDate beginAt;
    private LocalDate endAt;
    private Long createdBy;
    private int memberCnt;

    public static DiaryData from(Diary diary) {
        DiaryData diaryData = new DiaryData();
        
        diaryData.diaryNo = diary.getNo();
        diaryData.name = diary.getName();
        diaryData.type = diary.getType().toString();
        diaryData.hexColor = diary.getHexColor();
        diaryData.announcement = diary.getAnnouncement();
        diaryData.beginAt = diary.getPeriod().getBeginAt();
        diaryData.endAt = diary.getPeriod().getEndAt();
        diaryData.createdBy = diary.getModificationInfo().getCreatedBy();

        return diaryData;
    }
}
