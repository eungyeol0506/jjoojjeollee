package app.project.jjoojjeollee.dto.diary;

import lombok.Getter;

@Getter
public class DiaryEntryDto {

    private Long entryNo;
    private String title;
    private String contents;

    private String writerName;
    private Long createdBy;

    private Long imageNo;
    private String storedFilePath;
    private String storedFileName;
    private String extension;

    private int commentCount;
}
