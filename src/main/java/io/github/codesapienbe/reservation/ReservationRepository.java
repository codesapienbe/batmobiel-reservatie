package io.github.codesapienbe.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query("select r from Reservation r where r.end > :now")
    List<Reservation> findActive(ZonedDateTime now);

    @Query("select r from Reservation r where r.start < :to and r.end > :from")
    List<Reservation> findByRange(ZonedDateTime from, ZonedDateTime to);

    Page<Reservation> findAll(Pageable pageable);
}


