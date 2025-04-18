package app.project.jjoojjeollee.service.user;

import app.project.jjoojjeollee.global.exception.ServiceException;

public class PasswordMismatchException extends ServiceException {
    public PasswordMismatchException() {
        super(UserErrorCode.INVALID_PASSWORD);
    }
}
