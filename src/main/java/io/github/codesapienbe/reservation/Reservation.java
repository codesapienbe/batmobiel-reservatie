package io.github.codesapienbe.reservation;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(name = "start_time", nullable = false)
    private ZonedDateTime start;

    @Column(name = "end_time", nullable = false)
    private ZonedDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    protected Reservation() {}

    public Reservation(UUID id, String userId, ZonedDateTime start, ZonedDateTime end, ReservationStatus status) {
        this.id = id;
        this.userId = userId;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public UUID getId() { return id; }
    public String getUserId() { return userId; }
    public ZonedDateTime getStart() { return start; }
    public ZonedDateTime getEnd() { return end; }
    public ReservationStatus getStatus() { return status; }

    public void setStatus(ReservationStatus status) { this.status = status; }
}


