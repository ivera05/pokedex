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
__Backend__: Kotlin, Spring Boot, JPA, Flyway
__Frontend__: Next.js, React, TypeScript
__Database__: PostgreSQL
__Containerization__: Docker, Docker Compose
__Tools__: Maven, PNPM, ESLint, Prettier

## Project Setup
### Prerequisites
Ensure you have the following tools installed:

* __Java 17+__
* __Node.js 18+__
* __Maven 4+__ (for backend)
* __PNPM__ (for frontend)
* __Docker__ (optional, but recommended)

### Backend Setup
1. Navigate to the `backend` directory:

```bash
cd backend
```

2. Create a .env file in the backend directory with the following content:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/your_database
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password
```

3. Run the Flyway migration to set up the database schema:

```bash
./gradlew flywayMigrate
```

4. Build and run the backend:

```bash
./gradlew bootRun
```

By default, the Spring Boot application runs on http://localhost:8080.

### Frontend Setup
1. Navigate to the `frontend` directory:

```bash
cd frontend
```

2. Install the dependencies:

```bash
yarn install
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

2. Run the following command to start both the frontend and backend services:

```bash
docker-compose up --build
```

3. This will set up the PostgreSQL database, run the backend on port `8080`, and the frontend on port `3000`.

### Without Docker
1. Follow the Backend Setup and Frontend Setup instructions above.
2. Start the backend and frontend servers in their respective directories.

## API Documentation
The API exposes several endpoints for managing resources. Here's a basic list of endpoints (expand as necessary for your app):

* GET /api/pokemons: Fetch all Pokemons.
* POST /api/pokemons: Create a new Pokemon.
* GET /api/pokemons/{id}: Fetch a specific Pokemon by ID.
* PUT /api/pokemons/{id}: Update a specific Pokemon.

For a detailed API documentation, visit `/swagger-ui.html` (if you have Swagger enabled) or use tools like Postman to interact with the API.

