package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

@Entity
public class Diary {
    @Id @GeneratedValue
    @Column(name="diary_no")
    private Long no;

    @Column(name="diary_name")
    private String name;
    @Column(name="diary_type")
    private DiaryType type;

    @Embedded
    private ModificationInfo modificationInfo;
    private int currentIndex;
}
