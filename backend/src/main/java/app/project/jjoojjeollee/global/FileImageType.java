package app.project.jjoojjeollee.global;

public enum FileImageType {
    PROFILE("profiles"),
    DIARY("diaries");

    private final String directory;

    FileImageType(String directory) {
        this.directory = directory;
    }
    public String getDirectory() {
        return directory;
    }
}
