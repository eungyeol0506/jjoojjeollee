package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.common.ModificationInfo;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "diaries")
@Getter
public class Diary {
    @Id @GeneratedValue
    @Column(name = "diary_no", updatable = false)
    private Long no;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaryType type;

    @Column(name = "hex_color")
    private String hexColor;

    @Embedded
    private DiaryPeriod period;

    @Embedded
    private ModificationInfo modificationInfo;

    @Column(name = "current_index", nullable = false)
    private int currentIndex;

    @OneToMany(mappedBy = "diary")
    private List<DiaryEntry> diaryEntries;
}
