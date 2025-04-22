package app.project.jjoojjeollee.global.helper;

import java.util.UUID;

/**
 * 파일 경로 생성 전담 클래스
 * ex. 프로필 저장경로, 일기 첨부이미지 저장경로
 */
public class FilePathHelper {
    public static String generateProfileName(Long userNo, String originalFileName) {
        return String.format("profiles/%d_profile.%s", userNo, getExtension(originalFileName));
    }
    public static String generateDiaryName(Long diaryEntryNo, String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        return String.format("diaries/%d/%s.%s", diaryEntryNo, uuid, getExtension(originalFileName));
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
    public static String getFileNameOlny(String fullFilePathName){
        if (fullFilePathName == null || fullFilePathName.isEmpty()) {
            return "";
        }
        // 1. 마지막 / 뒤 파일명 추출
        fullFilePathName = fullFilePathName.replace("\\", "/"); // 윈도우 호환
        int lastSlashIdx = fullFilePathName.lastIndexOf('/');
        String fileName = (lastSlashIdx != -1) ? fullFilePathName.substring(lastSlashIdx + 1) : fullFilePathName;

        // 2. 파일명에서 확장자 제거
        int dotIdx = fileName.lastIndexOf('.');
        return (dotIdx != -1) ? fileName.substring(0, dotIdx) : fileName;
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
