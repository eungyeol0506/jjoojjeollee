package app.project.jjoojjeollee.global.helper;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 경로 생성 전담 클래스
 * ex. 프로필 저장경로, 일기 첨부이미지 저장경로
 */
public class FilePathHelper {
    public static String generateProfileName(Long userNo, String originalFileName) {
        return String.format("profiles/%d_profile.%s", userNo, getExtension(originalFileName));
    }
    public static String generateDiaryName(Long diaryNo, String originalFileName) {
        return String.format("diaries/%d_profile.%s", diaryNo, getExtension(originalFileName));
    };

    // 확장자만 추출
    public static String getExtension(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        if(idx == -1) {
            return "";
        }
        return originalFileName.substring(idx + 1);
    }

    // 파일 명만 추출
    public static String getFileName(String fullFilePathName){
        if (fullFilePathName == null || fullFilePathName.isEmpty()) {
            return "";
        }
        int idx = fullFilePathName.lastIndexOf('/');
        return idx != -1 ? fullFilePathName.substring(idx + 1) : fullFilePathName;
    }

    // 디렉터리 경로만 추출 (상대경로)
    public static String getDirectoryPath(String fullFilePathName) {
        if (fullFilePathName == null || fullFilePathName.isEmpty()) {
            return "";
        }
        fullFilePathName = fullFilePathName.replace("\\", "/");
        int idx = fullFilePathName.lastIndexOf('/');
        return (idx != -1) ? fullFilePathName.substring(0, idx) : "";
    }

}
