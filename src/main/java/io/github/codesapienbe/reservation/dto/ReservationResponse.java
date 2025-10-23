package io.github.codesapienbe.reservation.dto;

import io.github.codesapienbe.reservation.ReservationStatus;
import java.time.ZonedDateTime;
import java.util.UUID;

public record ReservationResponse(UUID id, String userId, ZonedDateTime start, ZonedDateTime end, ReservationStatus status) {}


