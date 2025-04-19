package app.project.jjoojjeollee.global;

import app.project.jjoojjeollee.global.helper.FilePathHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalFileStorageService implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String save(MultipartFile file, FileImageType fileType, Long no) {
        try {
            // 저장 경로
            String relativePathName = getRelativePath(fileType, no, file.getOriginalFilename());
            Path fullPath = Paths.get(uploadDir).resolve(relativePathName);

            if (Files.notExists(fullPath.getParent())) {
                Files.createDirectories(fullPath.getParent());
            }

            file.transferTo(fullPath.toFile());

            return relativePathName;

        }catch(IOException e) {
            throw new IllegalStateException("파일 저장 실패");
        }
    }

    @Override
    public void delete(String relativePathName){
        try{
            Path fullPath = Paths.get(uploadDir).resolve(relativePathName);

            Files.deleteIfExists(fullPath);
        } catch (IOException e) {
            throw new IllegalStateException("파일 삭제 실패");
        }
    }

    private String getRelativePath(FileImageType fileType, Long no, String originalFileName) {
        if(fileType == FileImageType.PROFILE){
            return FilePathHelper.generateProfileName(no, originalFileName);
        }
        if (fileType == FileImageType.DIARY){
            return FilePathHelper.generateDiaryName(no, originalFileName);
        }
        throw new IllegalArgumentException("Unsupported file type");
    }
}
