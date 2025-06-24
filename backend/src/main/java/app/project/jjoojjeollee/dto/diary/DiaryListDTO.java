package app.project.jjoojjeollee.dto.diary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiaryListDTO {
    private Long diaryNo;
    private String name;
    private String hexColor;
    private String announcement;
    private String type;
    private LocalDate endAt;
}
