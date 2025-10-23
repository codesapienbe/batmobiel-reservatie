```markdown
# 🚗 Batmobiel Reservatie API

A minimal, production-ready Spring Boot REST API for managing vehicle reservations.

## 📋 Features

- **Create Reservations** - Book vehicles with date/time ranges
- **List Reservations** - Paginated endpoint for browsing all bookings
- **Active Reservations** - Filter reservations that are currently active
- **Delete Reservations** - Remove bookings by ID
- **Multi-Environment Support** - H2 for development, PostgreSQL for production
- **Data Validation** - Request validation using Java records and Bean Validation

## 🛠 Tech Stack

- **Java 21** (or compatible version)
- **Spring Boot 3.x**
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Production database
- **H2** - In-memory database for development/testing
- **Maven** - Build and dependency management

## 🚀 Getting Started

### Prerequisites

- Java 21+ installed
- Maven 3.6+ installed
- PostgreSQL 12+ (for production deployment)

### Installation

1. Clone the repository:
```
git clone https://github.com/codesapienbe/batmobiel-reservatie.git
cd batmobiel-reservatie
```

2. Build the project:
```
mvn clean install
```

### Running the Application

#### Development Mode (H2 Database)

```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The application will start on `http://localhost:8080` with an in-memory H2 database.

#### Production Mode (PostgreSQL)

1. Set the database connection:
```
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/reservatie
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
```

2. Run the application:
```
mvn spring-boot:run
```

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/reservations` | Create a new reservation |
| `GET` | `/reservations` | Get all reservations (paginated) |
| `GET` | `/reservations/active` | Get active reservations (end > now) |
| `DELETE` | `/reservations/{id}` | Delete a reservation by ID |

### Example Request

**Create Reservation:**
```
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "vehicleId": "BAT-001",
    "customerName": "Bruce Wayne",
    "startTime": "2025-10-24T09:00:00",
    "endTime": "2025-10-24T17:00:00"
  }'
```

**List Reservations:**
```
curl http://localhost:8080/api/reservations?page=0&size=10
```

## 🧪 Testing

Run all tests:
```
mvn test
```

Run with coverage:
```
mvn clean test jacoco:report
```

## ⚙️ Configuration

### Application Properties

Key configuration options in `application.properties`:

```
# Database
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | - |
| `SPRING_DATASOURCE_USERNAME` | Database username | - |
| `SPRING_DATASOURCE_PASSWORD` | Database password | - |
| `SERVER_PORT` | Application port | 8080 |

## 🐳 Docker Support

Build and run with Docker:

```
# Build
docker build -t batmobiel-reservatie .

# Run
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/reservatie \
  batmobiel-reservatie
```

## 📦 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── be/codesapien/batmobiel/
│   │       ├── controller/    # REST controllers
│   │       ├── dto/           # Data Transfer Objects (records)
│   │       ├── entity/        # JPA entities
│   │       ├── repository/    # Spring Data repositories
│   │       └── service/       # Business logic
│   └── resources/
│       └── application.properties
└── test/
    └── java/                  # Unit and integration tests
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

Created by [codesapienbe](https://github.com/codesapienbe)

---

**Need help?** Open an issue on [GitHub](https://github.com/codesapienbe/batmobiel-reservatie/issues)
```

This README provides:

### Developer-Friendly Elements
- Clear tech stack and prerequisites
- Multiple running modes with exact commands
- API endpoint documentation with curl examples
- Configuration options and environment variables
- Testing instructions
- Project structure overview

### End-User-Friendly Elements
- Emoji icons for visual appeal
- Clear feature list
- Step-by-step installation guide
- Table format for better readability
- Docker support for easy deployment
- Professional formatting with proper sections

The README is production-ready and follows modern documentation standards while being accessible to both technical and non-technical stakeholders.

[1](https://github.com/codesapienbe/batmobiel-reservatie)