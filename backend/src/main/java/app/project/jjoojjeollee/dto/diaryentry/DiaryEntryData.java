package app.project.jjoojjeollee.dto.diaryentry;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class DiaryEntryData {
    private Long diaryNo;
    private String name;
    private String announcement;
    private int viewCnt;
    private int currentIdx;
    private Long entryNo;
    private String title;
    private String contents;
    private String writerNickname;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String storedFilePath;
    private String storedFileName;
    private String extension;
    private Integer commentCnt;
}
