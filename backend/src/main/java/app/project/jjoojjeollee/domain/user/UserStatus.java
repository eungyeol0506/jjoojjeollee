package app.project.jjoojjeollee.domain.user;

public enum UserStatus {
    ACTIVE,                 // 정상 사용자
    EMAIL_NOT_VERIFIED,     // 이메일 인증되지 않은 사용자
    LOCKED,                 // 잠금 사용자
    WITHDRAW_PENDING,       // 탈퇴요청 후 30일 미만 사용자0
    WITHDRAWN;              // 탈퇴처리가 완료된 사용자

    public boolean isAccessible() {
        return this == ACTIVE;
    }

    public boolean isRecoverable() {
        return this == EMAIL_NOT_VERIFIED || this == LOCKED || this == WITHDRAW_PENDING;
    }
}
