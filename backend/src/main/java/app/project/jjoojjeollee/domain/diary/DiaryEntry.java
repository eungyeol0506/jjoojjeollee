package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.common.Image;
import app.project.jjoojjeollee.domain.common.ModificationInfo;
import app.project.jjoojjeollee.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diary_entries")
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_no", updatable = false)
    private Long no;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "writer_nickname")
    private String writerName;

    @Embedded
    private ModificationInfo modificationInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_no")
    private Image image;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no")
    private List<Comment> comments = new ArrayList<>();

    /**
     *  일기 작성 메서드
     */
    public static DiaryEntry createDiaryEntry(String title, String contents, Image image, Diary diary, User author) {
        DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.setTitle(title);
        diaryEntry.setContents(contents);
        diaryEntry.setDiary(diary);
        diaryEntry.setImage(image);

        ModificationInfo modificationInfo = new ModificationInfo();
        modificationInfo.setCreated(author.getNo(), LocalDateTime.now());
        diaryEntry.setModificationInfo(modificationInfo);

        diaryEntry.setWriterName(author.getProfile().getNickname());
        
        return diaryEntry;
    }

    /**
     * 일기 내용 수정 메서드
     */
    public void editDiaryEntry(String title, String contents, Image image) {
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.modificationInfo.setUpdated( this.modificationInfo.getCreatedBy(), LocalDateTime.now());
    }
    /**
     * 일기 삭제 메서드
     */
    public void deleteDiaryEntry() {
        this.modificationInfo.setDeleted(this.modificationInfo.getCreatedBy(), LocalDateTime.now());
    }
    /**
     * 비공개로 수정 메서드 - 컬럼추가해야함
     */

}
