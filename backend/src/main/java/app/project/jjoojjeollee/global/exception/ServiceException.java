package app.project.jjoojjeollee.global.exception;

import app.project.jjoojjeollee.service.user.UserErrorCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private UserErrorCode errorCode;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
