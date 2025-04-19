package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.domain.Image;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.global.ApiResponse;
import app.project.jjoojjeollee.global.FileImageType;
import app.project.jjoojjeollee.global.LocalFileStorageService;
import app.project.jjoojjeollee.global.helper.FilePathHelper;
import app.project.jjoojjeollee.param.user.UserLoginParam;
import app.project.jjoojjeollee.param.user.UserProfileSettupParam;
import app.project.jjoojjeollee.param.user.UserRegisterParam;
import app.project.jjoojjeollee.repository.UserRepository;
import app.project.jjoojjeollee.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;
    private final LocalFileStorageService fileService;

    @PostMapping("/signup")
    public ResponseEntity createUser(@Valid @RequestBody UserRegisterParam param) {
        Long userNo = userService.join(param);

        return ResponseEntity.status(201)
                .body(ApiResponse.success(userNo));
    }

    @PostMapping("login")
    public ResponseEntity login(HttpSession session,
                                      @Valid @RequestBody UserLoginParam param) {
        Long userNo = userService.login(param);

        session.setAttribute("user", userNo);

        Map<String, Object> data = new HashMap<>();
        data.put("redirectUrl", "home");
        // jwt 토큰 발급 과정으로 변경 필요
        data.put("token", userNo);

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

    @GetMapping("profile")
    public ResponseEntity getUserProfile(HttpSession session) {
        Long userNo = (long) session.getAttribute("user");

        User user = userService.findUserWithProfile(userNo);

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PostMapping("settup")
    public ResponseEntity setupUserProfile(
            @RequestPart(required = false) MultipartFile file,
            @RequestPart @Valid UserProfileSettupParam param,
            HttpSession session) {

            Long userNo = (long) session.getAttribute("user");

            Image image = null;

            if(file != null && !file.isEmpty()) {
                String relativePathName = fileService.save(file, FileImageType.PROFILE, userNo);
                String storedFileName = FilePathHelper.getFileName(relativePathName);
                String storedFilePath = FilePathHelper.getDirectoryPath(relativePathName);
                String extension = FilePathHelper.getExtension(relativePathName);

                image = Image.createImage(file.getOriginalFilename(), storedFileName, storedFilePath, extension);

            }
            userService.saveUserProfile(userNo, param, image);

            return ResponseEntity.ok(ApiResponse.success("settup OK"));
    }
}
