package io.github.codesapienbe.reservation;

import io.github.codesapienbe.reservation.dto.ReservationCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository repo;

    private ReservationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ReservationService(repo);
    }

    @Test
    void create_savesReservation() {
        var now = ZonedDateTime.now();
        var req = new ReservationCreateRequest("user-1", now, now.plusHours(1));

        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var res = service.create(req);

        assertThat(res.userId()).isEqualTo("user-1");
        verify(repo, times(1)).save(any(Reservation.class));
    }

    @Test
    void list_returnsPage() {
        var r = new Reservation(UUID.randomUUID(), "u", ZonedDateTime.now(), ZonedDateTime.now().plusHours(1), ReservationStatus.CREATED);
        when(repo.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(r)));

        var page = service.list(PageRequest.of(0,10));

        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    void deleteById_marksDeleted() {
        var id = UUID.randomUUID();
        var r = new Reservation(id, "u", ZonedDateTime.now(), ZonedDateTime.now().plusHours(1), ReservationStatus.CREATED);
        when(repo.findById(id)).thenReturn(Optional.of(r));

        var deleted = service.deleteById(id);

        assertThat(deleted).isPresent();
        assertThat(deleted.get().status()).isEqualTo(ReservationStatus.DELETED);
        verify(repo).save(r);
    }

    @Test
    void durationInMinutes_returnsCorrectValue() {
        var id = UUID.randomUUID();
        var start = ZonedDateTime.now();
        var r = new Reservation(id, "u", start, start.plusMinutes(90), ReservationStatus.CREATED);
        when(repo.findById(id)).thenReturn(Optional.of(r));

        var dur = service.durationInMinutes(id);

        assertThat(dur).isPresent();
        assertThat(dur.get()).isEqualTo(90L);
    }

    @Test
    void countActiveByUser_returnsCount() {
        when(repo.countActiveByUser("u", any(java.time.ZonedDateTime.class))).thenReturn(3L);
        var count = service.countActiveByUser("u");
        assertThat(count).isEqualTo(3L);
    }
}


