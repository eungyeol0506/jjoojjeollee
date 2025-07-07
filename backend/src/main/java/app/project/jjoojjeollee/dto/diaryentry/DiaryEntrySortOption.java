package app.project.jjoojjeollee.dto.diaryentry;

public enum DiaryEntrySortOption {
    UPDATED_AT("updatedAt"),
    ViEW_COUNT("viewCount");

    private final String value;

    DiaryEntrySortOption(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public static DiaryEntrySortOption from(String value) {
        for(DiaryEntrySortOption option : DiaryEntrySortOption.values()) {
            if(option.getValue().equalsIgnoreCase(value)) {
                return option;
            }
        }
        throw new IllegalArgumentException("지정되지 않은 정렬 옵션입니다.");
    }
}
