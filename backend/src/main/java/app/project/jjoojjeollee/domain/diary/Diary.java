package app.project.jjoojjeollee.domain.diary;

import app.project.jjoojjeollee.domain.common.ModificationInfo;
import app.project.jjoojjeollee.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diaries")
@Getter @Setter(AccessLevel.PRIVATE)
public class Diary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_no", updatable = false)
    private Long no;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaryType type;

    @Column(name = "hex_color")
    private String hexColor;
    @Column(name = "announcement")
    private String announcement;

    @Embedded
    private DiaryPeriod period;

    @Embedded
    private ModificationInfo modificationInfo;

    @Column(name = "current_idx", nullable = false)
    private int currentIndex;

    @Column(name = "view_cnt", nullable = false)
    private int viewCnt;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiaryMember> diaryMembers = new ArrayList<>();

    //    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY)
    //    private List<DiaryEntry> diaryEntries;
    /**
     * 다이어리 생성 메서드
     */
    public static Diary createDiary(String name, String hexColor, String type, int dDay, User creator) {
        Diary diary = new Diary();
        LocalDateTime now = LocalDateTime.now();
        /* 일기 이름 */
        diary.setName(name);
        /* 표지 색상 */
        diary.setHexColor(hexColor.toUpperCase());
        /* 일기 타입 */
        if (type.equalsIgnoreCase("shared")){
            diary.setType(DiaryType.SHARED);
        }else {
            diary.setType(DiaryType.DEFAULT);
        }
        /* 작성 기간 */
        LocalDate endDate = LocalDate.now().plusDays(dDay);
        DiaryPeriod period = DiaryPeriod.builder()
                                        .endDate(endDate)
                                        .build();
        diary.setPeriod(period);
        /* 생성일 기록 */
        ModificationInfo mi = new ModificationInfo();
        mi.setCreated(creator.getNo(), now);
        diary.setModificationInfo(mi);
        /* 현재 작성자 순서 */
        int idx = 0;
        diary.setCurrentIndex(idx);
        /* 조회수 */
        diary.setViewCnt(0);
        /* 생성자를 멤버에 추가*/
        diary.addMember(creator, idx);
        return diary;
    }

    /**
     * 멤버 추가 메서드
     */
    public void addMember(User member, int idx){
        DiaryMember diaryMember = DiaryMember.createDiaryMember(this, member, idx);
        this.diaryMembers.add(diaryMember);
    }
    /**
     * 멤버 삭제 메서드
     */
    public void removeMember(User member){
        for(DiaryMember diaryMember : this.diaryMembers){
            if(diaryMember.getMember().equals(member)){
                this.diaryMembers.remove(diaryMember);
                break;
            }
        }
        /* idx 재정렬 */
        int idx = 0;
        this.diaryMembers.sort(DiaryMember::compareTo);

        for(DiaryMember diaryMember : this.diaryMembers){
            diaryMember.changeIdx(idx++);
        }
    }
    /**
     * 멤버 다음 순서로 변경 메서드
     */
    public void nextIdx(){
        int nextIndex = currentIndex + 1;
        if(nextIndex >= this.diaryMembers.size()){
            nextIndex = 0;
        }
        this.currentIndex = nextIndex;
    }
    /**
     * 일기 표지 수정 메서드
     */
    public void setupDiary(String name, String hexColor, Long userNo) {
        this.setName(name);
        this.setHexColor(hexColor);

        this.changeUpdateInfo(userNo);
    }
    /**
     * 일기 내 업데이트 발생 시 정보 갱신 메서드
     */
    public void changeUpdateInfo(Long userNo){
        this.modificationInfo.setUpdated(userNo, LocalDateTime.now());
    }
    /**
     * 조회수 갱신 메서드
     */
    public void updateViewCnt(){
        this.viewCnt++;
    }
    /**
     * 일기 공지사항 수정 메서드
     */
    public void changeAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    /**
     * 삭제 메서드
     */
    public void deleteDiary(Long userNo){
        this.modificationInfo.setDeleted(userNo,LocalDateTime.now());
    }

}
