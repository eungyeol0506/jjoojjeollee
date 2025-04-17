package app.project.jjoojjeollee.service.user;

import app.project.jjoojjeollee.domain.user.UserStatus;
import app.project.jjoojjeollee.global.exception.ServiceException;
import lombok.NoArgsConstructor;

public class RecoverableUserException extends ServiceException {
    public RecoverableUserException(UserStatus userStatus) {
        super(mapToErrorCode(userStatus));
    }

    private static UserErrorCode mapToErrorCode(UserStatus userStatus) {
        return switch(userStatus){
            case LOCKED -> UserErrorCode.USER_LOCKED;
            case WITHDRAW_PENDING -> UserErrorCode.USER_WITHDRAW;
            case EMAIL_NOT_VERIFIED -> UserErrorCode.USER_NOT_VERIFIED;
            default -> throw new IllegalArgumentException("Recoverable 한 사용자 상태가 아닌데 생성시도");
        };
    }
}
