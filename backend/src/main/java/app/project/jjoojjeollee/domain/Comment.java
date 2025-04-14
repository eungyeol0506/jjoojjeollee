package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

@Entity
public class Comment {
    @Id @GeneratedValue
    @Column(name="comment_no")
    private Long no;

    private String contents;

    @ManyToOne
    private DiaryEntry diaryEntry;

    @Embedded
    private ModificationInfo modificationInfo;

    @ManyToOne
    private Comment parent;
}
