package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

@Entity
public class DiaryEntry {
    @Id @GeneratedValue
    @Column(name="entry_no")
    private Long no;

    private String title;

    @ManyToOne
    private Diary diary;

    private String contents;

    @Embedded
    private ModificationInfo modificationInfo;

    @OneToOne
    private Image image;
}
