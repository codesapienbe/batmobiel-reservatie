package io.github.codesapienbe.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String username,


        @NotBlank
        @Size(min = 8, max = 128)
        String password
) {}


