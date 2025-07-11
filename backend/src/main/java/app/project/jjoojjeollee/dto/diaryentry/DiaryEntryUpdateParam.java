package app.project.jjoojjeollee.dto.diaryentry;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiaryEntryUpdateParam {
    private Long diaryNo;
    private Long entryNo;
    private DiaryEntryContent entryContent;
}
