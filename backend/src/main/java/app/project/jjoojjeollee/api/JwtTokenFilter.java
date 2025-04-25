package app.project.jjoojjeollee.api;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.global.ApiResponse;
import app.project.jjoojjeollee.repository.UserRepository;
import app.project.jjoojjeollee.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final String header = "Authorization";
    private final ObjectMapper objectMapper;

    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws IOException, ServletException {
        try{
            String token = getTokenFromHeader(request.getHeader(header)).orElseThrow(
                    () -> new AuthenticationException("토큰이 없습니다."){}
            );

            Long userNo = jwtService.getSubFromToken(token).orElseThrow(
                    () -> new AuthenticationException("토큰 내 사용자 정보가 없습니다.") {}
            );

            if(SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByNo(userNo).orElseThrow(
                        () -> new AuthenticationException("찾을 수 없는 사용자입니다.") {}
                );

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            // 정상흐름일 때만 다음 필터로
            filterChain.doFilter(request, response);
        }catch (AuthenticationException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = objectMapper.writeValueAsString(ApiResponse.error(e.getMessage()));
            response.getWriter().write(json);
        }
    }

    private Optional<String> getTokenFromHeader(String header) {
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return Optional.of(header.substring(7));
        }
        return Optional.empty();
    }
}
