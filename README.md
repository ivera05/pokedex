# Pokedex
## A Kotlin Spring Boot and Next.js Fullstack Project
This project is a full-stack web application built with __Kotlin__ and __Spring Boot__ for the backend, and __Next.js__ with React for the frontend. It leverages __PostgreSQL__ as the database and __Docker__ for containerization.

## Table of Contents
* [Technologies Used](#technologies-used)
* [Project Setup](#project-setup)
  * [Backend Setup](#backend-setup)
  * [Frontend Setup](#frontend-setup)
* [Running the Project](#running-the-project)
  * [Docker Setup](#docker-setup)
  * [Without Docker](#without-docker)
* [API Documentation](#api-documentation)

## Technologies Used
* __Backend__: Kotlin, Spring Boot, JPA, Flyway
* __Frontend__: Next.js, React, TypeScript
* __Database__: PostgreSQL
* __Containerization__: Docker, Docker Compose
* __Tools__: Maven, PNPM, ESLint, Prettier

## Project Setup
### Prerequisites
Ensure you have the following tools installed:

* __Java 17+__
* __Node.js 18+__
* __Maven__ (for backend)
* __PNPM__ (for frontend)
* __Docker__ (optional, but recommended)

### Backend Setup
1. Navigate to the `backend` directory:

```bash
cd backend
```

2. Create `application.properties` from example:

```bash
cp src/main/kotlin/com/pokedex/resources/application.properties.example src/main/kotlin/com/pokedex/resources/application.properties
```

3. Update properties with the following content:

```bash
spring.datasource.url=jdbc:postgresql://pokedex-db:5432/{your_database}
spring.datasource.username={your_db_user}
spring.datasource.password={your_db_password}
spring.flyway.url=jdbc:postgresql://pokedex-db:5432/{your_datavase}
spring.flyway.user={your_db_user}
spring.flyway.password={your_db_password}
```

3. Run the Flyway migration to set up the database schema:

```bash
./mvnw flyway:migrate
```

4. Build and run the backend:

```bash
./mvnw spring-boot:run
```

By default, the Spring Boot application runs on http://localhost:8080.

### Frontend Setup
1. Navigate to the `frontend` directory:

```bash
cd frontend
```

2. Install the dependencies:

```bash
pnpm install
```

3. Create a .env.local file in the `frontend` directory and set up the API URL:

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

4. Start the frontend development server:

```bash
pnpm dev
```
By default, the Next.js application runs on http://localhost:3000.

## Running the Project
### Docker Setup
The project is Dockerized, so you can run both the frontend and backend using Docker Compose.

1. Ensure Docker is installed and running.

2. Update the database settings on `docker-compose.yml`, these should match the values on the backend `application.properties`

```yaml
pokedex-db:
    environment:
      - 'POSTGRES_DB={your_database}'
      - 'POSTGRES_PASSWORD={your_db_pasword}'
      - 'POSTGRES_USER={your_db_user}'
pokedex-kotlin:
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://pokedex-db:5432/{your_database}
      SPRING_DATASOURCE_USERNAME: {your_db_user}
      SPRING_DATASOURCE_PASSWORD: {your_db_password}
```

3. Run the following command to start the postgreSQL, frontend and backend services:

```bash
docker-compose up --build -d
```

3. This will set up the PostgreSQL database, run the backend on port `8080`, and the frontend on port `3000`.

### Without Docker
1. Follow the [Backend Setup](#backend-setup) and [Frontend Setup](#frontend-setup) instructions above.
2. Start the backend and frontend servers in their respective directories.

## API Documentation
The API exposes several endpoints for managing resources. Here's a basic list of endpoints (expand as necessary for your app):

* `GET /pokemon`: Fetch all Pokemons.
* `GET /pokemon/{id}`: Fetch a specific Pokemon by ID.
* `POST /pokemon/sync`: Syncs Pokemons.
* `GET /pokemon/top_speed`: Fetch Pokemons by speed, in descending order.

For a detailed API documentation, visit `/swagger-ui.html` (if you have Swagger enabled) or use tools like Postman to interact with the API.

## License
This project is licensed under the MIT License.