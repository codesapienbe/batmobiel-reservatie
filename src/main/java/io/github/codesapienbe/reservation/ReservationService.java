package io.github.codesapienbe.reservation;

import io.github.codesapienbe.reservation.dto.ReservationCreateRequest;
import io.github.codesapienbe.reservation.dto.ReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ReservationResponse create(ReservationCreateRequest req) {
        UUID id = UUID.randomUUID();
        Reservation r = new Reservation(id, req.userId(), req.start(), req.end(), ReservationStatus.CREATED);
        repository.save(r);
        return toResponse(r);
    }

    public Page<ReservationResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public List<ReservationResponse> active() {
        var now = ZonedDateTime.now();
        return repository.findActive(now).stream().map(this::toResponse).toList();
    }

    @Transactional
    public Optional<ReservationResponse> deleteById(UUID id) {
        return repository.findById(id).map(r -> {
            r.setStatus(ReservationStatus.DELETED);
            repository.save(r);
            return toResponse(r);
        });
    }

    private ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(r.getId(), r.getUserId(), r.getStart(), r.getEnd(), r.getStatus());
    }
}


