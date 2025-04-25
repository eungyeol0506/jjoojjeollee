package app.project.jjoojjeollee.service;

import app.project.jjoojjeollee.domain.user.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final int sessionTime;
    private final SignatureAlgorithm signatureAlgorithm;

    @Autowired
    public JwtService(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.session-time}") int sessionTime
    ){
        this.sessionTime = sessionTime;
        this.signatureAlgorithm = SignatureAlgorithm.HS512;
        this.secretKey = new SecretKeySpec(secretKey.getBytes(), signatureAlgorithm.getJcaName());
    }

    public String toToken(User user){
        return Jwts.builder()
                .setSubject(String.valueOf(user.getNo()))
                .setExpiration(expireTimeFromNow())
                .signWith(secretKey)
                .compact();
    }

    public Optional<Long> getSubFromToken(String token){
        try{
            String subject = Jwts.parserBuilder()
                                .setSigningKey(secretKey)
                                .build()
                                .parseClaimsJws(token)
                                .getBody()
                                .getSubject();

            if(subject == null) return Optional.empty();
            return Optional.of(Long.parseLong(subject));

        }catch (Exception e){
            return Optional.empty();
        }
    }
    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000L);
    }
}
