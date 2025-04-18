package app.project.jjoojjeollee.param.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterParam {
    @NotBlank(message = "필수값을 입력해주세요")
    @DuplicatedIdConstraint
    private String id;

    @NotBlank(message = "필수값을 입력해주세요")
    private String pw;

    @NotBlank(message = "필수값을 입력해주세요")
    @Email(message = "이메일 형식이 아닙니다")
    @DuplicatedEmailConstraint
    private String email;

}
