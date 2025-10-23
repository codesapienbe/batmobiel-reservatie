package io.github.codesapienbe.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import java.time.ZonedDateTime;

public record ReservationCreateRequest(
        @NotBlank String userId,
        @NotNull ZonedDateTime start,
        @NotNull ZonedDateTime end
){
    @AssertTrue(message = "end must be after start")
    private boolean isEndAfterStart() {
        if (start == null || end == null) return true;
        return end.isAfter(start);
    }
}


