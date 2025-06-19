package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.common.ModificationInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diaries")
@Getter @Setter(AccessLevel.PRIVATE)
public class Diary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_no", updatable = false)
    private Long no;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaryType type;

    @Column(name = "hex_color")
    private String hexColor;
    @Column(name = "announcement")
    private String announcement;

    @Embedded
    private DiaryPeriod period;

    @Embedded
    private ModificationInfo modificationInfo;

    @Column(name = "current_idx", nullable = false)
    private int currentIndex;

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY)
    private List<DiaryMember> diaryMembers = new ArrayList<>();

    //    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY)
    //    private List<DiaryEntry> diaryEntries;
    /**
     * 다이어리 생성 메서드
     */
    public static Diary createDiary(String name, String hexColor, String type, int dDay, Long userNo) {
        Diary diary = new Diary();
        LocalDateTime now = LocalDateTime.now();
        /* 일기 이름 */
        diary.setName(name);
        /* 표지 색상 */
        diary.setHexColor(hexColor);
        /* 일기 타입 */
        if (type.equalsIgnoreCase("shared")){
            diary.setType(DiaryType.SHARED);
        }else {
            diary.setType(DiaryType.DEFAULT);
        }
        /* 작성 기간 */
        LocalDate endDate = LocalDate.now().plusDays(dDay);
        DiaryPeriod period = DiaryPeriod.builder()
                                        .endDate(endDate)
                                        .build();
        diary.setPeriod(period);
        /* 생성일 기록 */
        ModificationInfo mi = new ModificationInfo();
        mi.setCreated(userNo, now);
        diary.setModificationInfo(mi);

        /* 다이어리 멤버 생성 */

        return diary;
    }
}
