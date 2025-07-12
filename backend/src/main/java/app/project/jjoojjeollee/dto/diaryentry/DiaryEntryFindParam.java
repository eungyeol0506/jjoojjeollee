package app.project.jjoojjeollee.dto.diaryentry;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiaryEntryFindParam {
    private Long diaryNo;
    private int month;
    private int page;
    private DiaryEntrySortOption orderBy;
}
