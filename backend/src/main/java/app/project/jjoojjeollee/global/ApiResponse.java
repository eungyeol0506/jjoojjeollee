package app.project.jjoojjeollee.global;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String error;

    public ApiResponse(boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(String errorMessage){
        return new ApiResponse<>(false, null, errorMessage);
    }
}

//field	설명
//status	HTTP 상태 코드
//errorCode	서버 내부 비즈니스 코드 (ex. USER_NOT_FOUND, EMAIL_ALREADY_EXISTS)
//message	사용자에게 보여줄 에러 메시지