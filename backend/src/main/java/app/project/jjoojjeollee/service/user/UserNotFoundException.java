package app.project.jjoojjeollee.service.user;

import app.project.jjoojjeollee.global.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
