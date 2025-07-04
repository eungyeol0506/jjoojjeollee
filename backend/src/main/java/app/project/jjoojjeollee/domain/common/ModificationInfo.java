package app.project.jjoojjeollee.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class ModificationInfo {
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_by")
    private Long deletedBy;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
