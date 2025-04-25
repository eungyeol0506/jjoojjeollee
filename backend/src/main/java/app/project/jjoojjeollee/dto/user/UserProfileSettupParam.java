package app.project.jjoojjeollee.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserProfileSettupParam {
    private String nickname;
    private String lineMessage;
}
