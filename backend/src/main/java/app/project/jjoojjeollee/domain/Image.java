package app.project.jjoojjeollee.domain;

import app.project.jjoojjeollee.global.FileImageType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    /**
     * 생성 메서드
     */
    public static Image createImage(String originalFileName, String storedFileName, String storedFilePath, String extension) {
        Image image = new Image();
        image.setOriginalFileName(originalFileName);
        image.setStoredFileName(storedFileName);
        image.setStoredFilePath(storedFilePath);
        image.setExtension(extension);

        return image;
    }

    /**
     * 이미지 상대경로 조회
     */
    @Transient
    public String getRelativePath() {
        return storedFilePath + "/" + storedFileName + "." + extension;
    }
}
