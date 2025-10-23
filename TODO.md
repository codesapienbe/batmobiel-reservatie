JWT + UserService implementation (progress)

Completed
1. Create TODO.md in repository ✅
2. Add User entity and JPA repository ✅
3. Add DTO records and UserService (register + login, BCrypt) ✅
4. Implement JwtProvider and JwtAuthenticationFilter ✅
5. Add SecurityConfig to secure endpoints and permit /auth and /users/register ✅
6. Create AuthController with /auth/login and /users/register ✅
7. Secure ReservationController endpoints and integrate auth principal ✅
8. Add unit tests for UserService and JwtProvider ✅
13. Add Open API docs and Swagger UI (springdoc) ✅
10. Extend reservation: Duration endpoint. Add GET /reservations/{id}/duration — return length in minutes; add unit test. ✅
11. Search by Date Range: GET /reservations/search?from=...&to=... — return overlapping reservations; add tests  ✅
12. Count Active Reservations per User: GET /reservations/active/count?userId=... — return integer; add tests  ✅

9. Populate the database for dev profile with 3 user groups: `ADMIN`, `MANAGER`, `CONSUMER` (in_progress)


Other suggestions
- Add CI workflow to run unit + integration tests on push
- Consider refresh tokens design and token revocation strategy

