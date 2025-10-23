package io.github.codesapienbe.user;

import io.github.codesapienbe.user.dto.AuthRequest;
import io.github.codesapienbe.user.dto.AuthResponse;
import io.github.codesapienbe.user.dto.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final io.github.codesapienbe.auth.JwtProvider jwtProvider;

    public UserService(UserRepository repository, io.github.codesapienbe.auth.JwtProvider jwtProvider) {
        this.repository = repository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (repository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("username already taken");
        }
        var id = UUID.randomUUID();
        var hash = passwordEncoder.encode(req.password());
        var user = new User(id, req.username(), hash);
        repository.save(user);
        var expiresAt = Instant.now().plus(jwtProvider.getExpirationSeconds(), ChronoUnit.SECONDS);
        var token = jwtProvider.generateToken(req.username());
        return new AuthResponse(token, expiresAt);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean verifyCredentials(AuthRequest req) {
        return repository.findByUsername(req.username())
                .map(u -> passwordEncoder.matches(req.password(), u.getPasswordHash()))
                .orElse(false);
    }
}


