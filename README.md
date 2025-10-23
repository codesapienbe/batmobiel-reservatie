# Reservatie API

Minimal Spring Boot Reservation API.

Features
- Create reservation
- List reservations (paged)
- Get active reservations (end > now)
- Delete reservation by id

Persistence: PostgreSQL (prod), H2 (dev/test)

DTOs implemented as Java records with validation annotations.

Run
- Dev (H2): `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- Prod (Postgres): configure `SPRING_DATASOURCE_URL` and run without `dev` profile.

Tests: `mvn test`


