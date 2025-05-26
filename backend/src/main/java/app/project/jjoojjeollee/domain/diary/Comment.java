package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.common.ModificationInfo;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "comments")
@Getter
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_no")
    private Long no;

    @Column(name = "comment_text", nullable = false, length = 100)
    private String commentText;

    @Embedded
    private ModificationInfo modificationInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_no")
    private DiaryEntry diaryEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_no", referencedColumnName = "comment_no")
    private Comment parent;
}
