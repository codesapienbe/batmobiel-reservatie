package io.github.codesapienbe.user.dto;

import java.time.Instant;

public record AuthResponse(String token, Instant expiresAt) {}


