package app.project.jjoojjeollee.dto.diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class DiaryCreateParam {

    private String name;
    private String type;
    private String hexColor;
    private LocalDate expiryDate;

}
