package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.domain.Image;
import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.dto.user.UserLoginParam;
import app.project.jjoojjeollee.dto.user.UserProfileSettupParam;
import app.project.jjoojjeollee.dto.user.UserRegisterParam;
import app.project.jjoojjeollee.global.ApiResponse;
import app.project.jjoojjeollee.global.FileImageType;
import app.project.jjoojjeollee.service.JwtService;
import app.project.jjoojjeollee.service.LocalFileStorageService;
import app.project.jjoojjeollee.global.helper.FilePathHelper;
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
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity createUser(@Valid @RequestBody UserRegisterParam param) {
        Long userNo = userService.join(param);

        Map<String, Object> data = new HashMap<>();
        data.put("userNo", userNo);
        return ResponseEntity.status(201)
                .body(ApiResponse.success(data));
    }

    @PostMapping("login")
    public ResponseEntity login(HttpSession session,
                                      @Valid @RequestBody UserLoginParam param) {
        Long userNo = userService.login(param);

        session.setAttribute("user", userNo);

        Map<String, Object> data = new HashMap<>();
        data.put("redirectUrl", "home");
        // jwt 토큰 발급 과정으로 변경 필요
        data.put("token", jwtService.toToken(userNo));

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

    @PostMapping(value = "settup")
    public ResponseEntity setupUserProfile(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "userProfileParam") @Valid UserProfileSettupParam param,
            @RequestPart boolean isRemovedImage,
            HttpSession session) {

            Long userNo = (long) session.getAttribute("user");

            Image image = null;

            if(file != null && !file.isEmpty()) {
                String relativePathName = fileService.save(file, FileImageType.PROFILE, userNo);
                String storedFileName = FilePathHelper.getFileNameOlny(relativePathName);
                String storedFilePath = FilePathHelper.getDirectoryPath(relativePathName);
                String extension = FilePathHelper.getExtension(relativePathName);

                image = Image.createImage(file.getOriginalFilename(), storedFileName, storedFilePath, extension);
            }
            if(isRemovedImage){
                String relativeFileName = userService.removeUserProfileImage(userNo);
                fileService.delete(relativeFileName);
            }

            userService.saveUserProfile(userNo, param, image);

            return ResponseEntity.ok(ApiResponse.success("settup OK"));
    }
}
