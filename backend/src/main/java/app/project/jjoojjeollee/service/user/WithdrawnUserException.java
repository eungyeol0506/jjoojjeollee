package app.project.jjoojjeollee.service.user;

import app.project.jjoojjeollee.global.exception.ServiceException;

public class WithdrawnUserException extends ServiceException {
    public WithdrawnUserException() {
        super(UserErrorCode.USER_WITHDRAW);
    }
}
