JWT + UserService implementation

1. Create TODO.md in repository (in_progress)
2. Add User entity and JPA repository
3. Add DTO records and UserService (register + login, BCrypt)
4. Implement JwtProvider and JwtAuthenticationFilter
5. Add SecurityConfig to secure endpoints and permit /auth and /users/register
6. Create AuthController with /auth/login and /users/register
7. Secure ReservationController endpoints and integrate auth principal
8. Add unit tests for UserService and JwtProvider
9. Populate the database for dev profile with 3 different user groups in `ADMIN`, `MANAGER`, and `CONSUMER`
10. Extend reservation: Duration endpoint. Add an endpoint GET /reservations/{id}/duration. Return the reservaiton length in minutes. implement service logic. add unit test. 
11. Search by Date Range: Add an endpoint GET /reservations/search?from=...&to=... returns reservations that overlap with a given date range. Requires logic JPQL or custom repo method. Test overlapping and non-overlapping periods.
12. Count Active Reservations per User. Add an endpoint GET /reservations/active/count?userId=... returns a single integer (number of active reservations). Repository method with COUNT. Unit test with edge case (no active reservation.)
13. Add Open API docs and Swagger UI to allow develoopers to easily test the API via http://localhost:8080/docs
