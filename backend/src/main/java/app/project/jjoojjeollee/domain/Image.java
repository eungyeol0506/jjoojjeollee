package app.project.jjoojjeollee.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "images")
@Getter
public class Image {
    @Id @GeneratedValue
    @Column(name = "image_no")
    private Long no;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "stored_file_name", nullable = false)
    private String storedFileName;

    @Column(name = "stored_file_path", nullable = false)
    private String storedFilePath;

    @Column(name = "extension", nullable = false)
    private String extension;
}
