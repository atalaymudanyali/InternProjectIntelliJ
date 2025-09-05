# QuizApp (Spring Boot + JSF/PrimeFaces)

A Spring Boot 3.3 application for managing quizzes, questions, users, and OTP-based login/reset flows. It combines REST APIs with JSF (Jakarta Faces) pages using PrimeFaces.

## Features
- User registration and login with OTP validation
- Quiz CRUD via REST APIs
- Question CRUD tied to quizzes via REST APIs
- Role-based access control (ADMIN/TEACHER/…)
- JSF pages for login, registration, create quiz/question
- PostgreSQL persistence with JPA/Hibernate
- Actuator endpoints (health/metrics)

## Tech Stack
- Java 17, Spring Boot 3.3
- Spring Web, Spring Security, Spring Data JPA, Validation, Actuator
- Jakarta Faces (JSF) 3.0, PrimeFaces 14
- Database: PostgreSQL (H2 also available as runtime dep)
- Build: Maven

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL running locally

### Clone and build
```bash
mvn -v
java -version

# from project root (contains pom.xml)
mvn clean package
```

### Configuration
App configuration is in `src/main/resources/application.properties`:
```properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/quizApp
spring.datasource.username=postgres
spring.datasource.password=12345
spring.mvc.view.prefix=/META-INF/resources/
spring.mvc.view.suffix=.xhtml
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework=DEBUG
logging.level.org.glassfish=DEBUG
```
Update `spring.datasource.*` for your local DB. The schema will be auto-updated (`ddl-auto=update`).

### Run the app
```bash
# Run with Maven
mvn spring-boot:run

# Or run the built jar
java -jar target/quizApp-0.0.1-SNAPSHOT.jar
```
Default port is 8080 unless overridden.

## Security
- Custom password encoder: `Base64PasswordEncoder` (encodes raw password as Base64). This is NOT secure for production; replace with `BCryptPasswordEncoder` before going live.
- Public endpoints are permitted for login/registration/OTP and static assets. All other requests require authentication.
- Selected JSF pages require `TEACHER` or `ADMIN` roles (see `SecurityConfig`).

Key config (simplified):
```java
http.csrf(AbstractHttpConfigurer::disable)
    .authorizeHttpRequests(auth -> auth
        .requestMatchers(
            "/login.xhtml","/api/register","/api/login",
            "/api/auth/login","api/auth/generate-otp",
            "/register.xhtml","/home/dashboard.xhtml","forgetPassword.xhtml",
            "/auth/generate-otp","/auth/validate-otp","/error",
            "/resources/**","/static/**","/webjars/**"
        ).permitAll()
        .requestMatchers("/home/createQuestion.xhtml","/home/createQuiz.xhtml").hasAnyRole("TEACHER","ADMIN")
        .anyRequest().authenticated()
    )
    .formLogin(form -> form.loginPage("/login.xhtml").permitAll())
    .logout(LogoutConfigurer::permitAll);
```

## REST API
Base URL: `http://localhost:8080`

### Auth
- POST `/api/auth/generate-otp`
  - Body: raw string with username (example: `"alice"`)
  - Response: `"OTP has been sent.<otp>"` (demo returns OTP in body)
- POST `/api/auth/login`
  - Body (`application/json`):
    ```json
    {"username":"alice","password":"secret","otp":"123456"}
    ```
  - Response: `"Login successful"` if OTP valid; otherwise 401

### Users
- POST `/api/login`
  - Body: `LoginRequest` (same as above)
  - Response: `ResponseObject` with `status`, `message`, `data`
- POST `/api/register`
  - Body (`application/json`):
    ```json
    {
      "username": "alice",
      "password": "secret",
      "fname": "Alice",
      "lname": "Doe",
      "email": "alice@example.com",
      "role": "STUDENT"  // or TEACHER, ADMIN, etc.
    }
    ```
  - Response: `ResponseObject`

### Quizzes
- POST `/api/quizzes`
  - Body (`Quiz`):
    ```json
    {
      "title": "Java Basics",
      "description": "Intro quiz",
      "active": true,
      "maxMarks": "100",
      "noOfQuestions": "10",
      "numQuestions": 10,
      "durationMinutes": 30,
      "faculty": "ENGINEERING",
      "category": { "categoryId": 1 }
    }
    ```
- GET `/api/quizzes` → returns `Set<Quiz>`
- GET `/api/quizzes/{quizId}` → returns `Quiz`
- PUT `/api/quizzes/{quizId}` → update `title`, `numQuestions` (5–20), `durationMinutes` (5–180)
- DELETE `/api/quizzes/{quizId}`

### Questions
- POST `/api/questions/quiz/{quizId}`
  - Body (`Question`):
    ```json
    {
      "questionWeight": 1.0,
      "faculty": "ENGINEERING",
      "content": "What is JVM?",
      "option1": "Java Virtual Machine",
      "option2": "",
      "option3": "",
      "option4": "",
      "option5": "",
      "answer": "option1"
    }
    ```
- GET `/api/questions/quiz/{quizId}` → returns `List<Question>`
- GET `/api/questions/{questionId}` → returns `Question`
- PUT `/api/questions/{questionId}` → update `content`, `answer`, etc.
- DELETE `/api/questions/{questionId}`

## JSF Pages (PrimeFaces)
- `login.xhtml`, `register.xhtml`
- `home/createQuiz.xhtml`, `home/createQuestion.xhtml`
- `forgetPassword.xhtml` (generates and verifies OTP, then resets password)

Faces config: `src/main/resources/META-INF/faces-config.xml`. Views resolved via:
```
prefix=/META-INF/resources/
suffix=.xhtml
```

## Development Notes
- The demo OTP flows and Base64 password encoder are for development. For production, use a secure OTP delivery (email/SMS) and BCrypt hashing.
- Role names in `SecurityConfig` expect authorities like `ROLE_TEACHER`, `ROLE_ADMIN`.
- Actuator is included; enable/secure endpoints as needed.

## Running Tests
```bash
mvn test
```

## Project Structure
```
src/main/java/com/internproject/quizApp/
  config/ (security, OTP utils, password encoder)
  controller/ (REST controllers & JSF backing beans)
  dto/ (request/response DTOs)
  model/ (entities: User, Quiz, Question, Category, Result, etc.)
  service/ (interfaces & implementations)
src/main/resources/
  META-INF/ (faces-config.xml, .xhtml pages)
  application.properties
```

## License
This project is currently unlicensed. Add a license file if needed.