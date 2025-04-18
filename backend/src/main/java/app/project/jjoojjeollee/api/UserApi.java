package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.global.ApiResponse;
import app.project.jjoojjeollee.param.user.UserLoginParam;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity createUser(@Valid @RequestBody UserRegisterParam param) {
        Long userNo = userService.join(param);

        return ResponseEntity.status(201)
                .body(ApiResponse.success(userNo));
    }

    @PostMapping("login")
    public ResponseEntity login(HttpSession session,
                                      @Valid @RequestBody UserLoginParam param) {
        User user = userService.login(param);
        // jwt 토큰 발급 과정으로 변경 필요
        session.setAttribute("user", user.getNo());
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PostMapping("logout")
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(ApiResponse.success("logout OK"));
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteUser(HttpSession session) {
        Long userNo = (long) session.getAttribute("user");
        userService.withdraw(userNo);

        return ResponseEntity.status(204)
                .body(ApiResponse.success("delete OK"));
    }
}
