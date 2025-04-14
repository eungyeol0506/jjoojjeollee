package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;

@Entity
public class DiaryMember {
    @Id @GeneratedValue
    @Column(name="member_no")
    private Long no;

    @ManyToOne
    private Diary diary;

    @ManyToOne
    private User writer;

    private int index;
}
