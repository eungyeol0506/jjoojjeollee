package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.common.Image;
import app.project.jjoojjeollee.domain.common.ModificationInfo;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "diary_entries")
@Getter
public class DiaryEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_no", updatable = false)
    private Long no;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "writer_name")
    private String writerName;

    @Embedded
    private ModificationInfo modificationInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_no")
    private Image image;
}
