package io.github.codesapienbe.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtProvider {

    private final SecretKey key;
    private final long expirationSeconds;

    public JwtProvider(JwtProperties props) {
        var secret = props.getSecret() == null ? "defaultsecretdefaultsecretdefault" : props.getSecret();
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationSeconds = props.getExpiration();
    }

    public String generateToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String subject(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}


