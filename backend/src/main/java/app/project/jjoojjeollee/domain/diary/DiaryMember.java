package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "diary_members")
@Getter
public class DiaryMember {
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

}
