package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import app.project.jjoojjeollee.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserRepository userRepository;
    private final UserService userService;

    @RequestMapping(path="/users/join", method = RequestMethod.POST)
    public ResponseEntity createUser(@Valid @RequestBody UserRegisterParam param) {
        Long userNo = userService.join(param);
        User user = userRepository.findByNo(userNo);
        return ResponseEntity.ok(user);
    }

//    @RequestMapping(path="/users/login", method = RequestMethod.POST)
//    public ResponseEntity login(){//login param
//
//    }
}
