package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "diary_members")
@Getter
public class DiaryMember implements Comparable<DiaryMember>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long no;

    @Column(name = "idx", nullable = false)
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User member;

    public static DiaryMember createDiaryMember(Diary diary, User user, int idx) {
        DiaryMember diaryMember = new DiaryMember();
        diaryMember.diary = diary;
        diaryMember.member = user;
        diaryMember.idx = idx;
        return diaryMember;
    }

    @Override
    public int compareTo(DiaryMember other) {
        return Integer.compare(this.idx, other.idx);
    }

    public void changeIdx(int idx) {
        this.idx = idx;
    }
}
