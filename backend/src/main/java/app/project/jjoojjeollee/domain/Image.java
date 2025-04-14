package app.project.jjoojjeollee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Image {
    @Id @GeneratedValue
    @Column(name="image_no")
    private Long no;
    private String originalFileName;
    private String storedFileName;
    private String storedFilePath;
    private String extension;
}
