package app.project.jjoojjeollee.domain;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class ModificationInfo {
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
    private Long deletedBy;
    private LocalDateTime deletedAt;
}
