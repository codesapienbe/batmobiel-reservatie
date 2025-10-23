package io.github.codesapienbe.reservation;

import io.github.codesapienbe.reservation.dto.ReservationCreateRequest;
import io.github.codesapienbe.reservation.dto.ReservationResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid ReservationCreateRequest request) {
        var res = service.create(request);
        return ResponseEntity.created(URI.create("/reservations/" + res.id())).body(res);
    }

    @GetMapping
    public Page<ReservationResponse> list(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        Pageable p = PageRequest.of(page, size);
        return service.list(p);
    }

    @GetMapping("/active")
    public List<ReservationResponse> active() {
        return service.active();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> delete(@PathVariable UUID id) {
        Optional<ReservationResponse> deleted = service.deleteById(id);
        return deleted.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


