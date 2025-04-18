package app.project.jjoojjeollee.param.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonRootName("login")
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginParam {
    @NotBlank(message = "아이디는 필수값입니다!")
    private String id;
    @NotBlank(message = "비밀번호는 필수값입니다!")
    private String pw;

    public boolean isEmail(){
        return id.contains("@");
    }
}
