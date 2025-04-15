package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "diary_entries")
@Getter
public class DiaryEntry {
    @Id @GeneratedValue
    @Column(name = "entry_no", updatable = false)
    private Long no;

    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "contents", nullable = false)
    private String contents;

    @Embedded
    private ModificationInfo modificationInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_no")
    private Image image;
}
