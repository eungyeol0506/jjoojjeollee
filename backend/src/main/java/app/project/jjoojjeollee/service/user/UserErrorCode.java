package app.project.jjoojjeollee.service.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    USER_NOT_VERIFIED(HttpStatus.FORBIDDEN, "이메일 인증이 필요한 사용자입니다."),
    USER_WITHDRAW(HttpStatus.FORBIDDEN, "탈퇴를 요청한 사용자입니다.(처리 대기중)"),
    USER_LOCKED(HttpStatus.FORBIDDEN, "잠금된 사용자입니다."),
    USER_NOT_RECOVER(HttpStatus.BAD_REQUEST, "완전히 탈퇴처리된 사용자입니다.");

    private final HttpStatus status;
    private final String message;
}
