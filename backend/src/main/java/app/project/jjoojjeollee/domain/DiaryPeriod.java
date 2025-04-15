package app.project.jjoojjeollee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Embeddable
@Getter
public class DiaryPeriod {
    @Column(name = "begin_at", nullable = false)
    private LocalDate beginAt;

    @Column(name = "end_at", nullable = false)
    private LocalDate endAt;

    public DiaryPeriod(){}

    @Builder
    public DiaryPeriod(LocalDate endDate) {
        this.beginAt = LocalDate.now();
        validateEndAt(endDate);
        this.endAt = endDate;
    }

    private void validateEndAt(LocalDate endDate) {
        if (endDate != null && endDate.isBefore(this.beginAt)){
            throw new IllegalArgumentException("End date must be after begin date");
        }
    }
}
