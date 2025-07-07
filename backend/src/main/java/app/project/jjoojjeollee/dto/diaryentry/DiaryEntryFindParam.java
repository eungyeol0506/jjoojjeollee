package app.project.jjoojjeollee.dto.diaryentry;

import lombok.Getter;

@Getter
public class DiaryEntryFindParam {
    private Long diaryNo;
    private int month;
    private int page;
    private DiaryEntrySortOption orderBy;
}
