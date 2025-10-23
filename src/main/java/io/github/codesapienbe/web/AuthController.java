package io.github.codesapienbe.web;

import io.github.codesapienbe.auth.JwtProvider;
import io.github.codesapienbe.user.UserService;
import io.github.codesapienbe.user.dto.AuthRequest;
import io.github.codesapienbe.user.dto.AuthResponse;
import io.github.codesapienbe.user.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest req) {
        var auth = userService.register(req);
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
        boolean ok = userService.verifyCredentials(req);
        if (!ok) return ResponseEntity.status(401).build();
        var token = jwtProvider.generateToken(req.username());
        var expiresAt = Instant.now().plusSeconds(jwtProvider.getExpirationSeconds());
        return ResponseEntity.ok(new AuthResponse(token, expiresAt));
    }
}


