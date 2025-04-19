package app.project.jjoojjeollee.global;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    public String save(MultipartFile file, FileImageType fileType, Long no);  // 덮어쓰기 기본
    public void delete(String path);
}
