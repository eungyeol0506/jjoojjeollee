package app.project.jjoojjeollee.global.exception;

import app.project.jjoojjeollee.global.ApiResponse;
import app.project.jjoojjeollee.service.user.PasswordMismatchException;
import app.project.jjoojjeollee.service.user.RecoverableUserException;
import app.project.jjoojjeollee.service.user.UserNotFoundException;
import app.project.jjoojjeollee.service.user.WithdrawnUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * user 관련 요청 handle
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse> handleServiceException(ServiceException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getErrorCode().getMessage()));
    }
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
//        return ResponseEntity.status(e.getErrorCode().getStatus())
//                .body(ApiResponse.error(e.getErrorCode().getMessage()));
//    }
//
//    @ExceptionHandler(RecoverableUserException.class)
//    public ResponseEntity handleRecoverableUserException(RecoverableUserException e) {
//        return ResponseEntity.status(e.getErrorCode().getStatus())
//                .body(ApiResponse.error(e.getMessage()));
//    }
//
//    @ExceptionHandler(PasswordMismatchException.class)
//    public ResponseEntity handlePasswordMismatchException(PasswordMismatchException e) {
//        return ResponseEntity.status(e.getErrorCode().getStatus())
//                .body(ApiResponse.error(e.getMessage()));
//    }
//    @ExceptionHandler(WithdrawnUserException.class)
//    public ResponseEntity handleWithdrawnUserException(WithdrawnUserException e) {
//        return ResponseEntity.status(e.getErrorCode().getStatus())
//                .body(ApiResponse.error(e.getMessage()));
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 메시지 내용 수집
        String message = e.getBindingResult().getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining("\n"));

        return ResponseEntity.status(400)
                .body(ApiResponse.error(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception exception) {
        return ResponseEntity.status(500)
                .body(ApiResponse.error(exception.getMessage()));
    }
}
