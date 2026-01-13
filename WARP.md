# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Repository structure

- Root `compose.yaml`: Docker Compose stack defining the `postgres` database and a `backend` service for the API (and a commented-out `frontend` service).
- `barbermind-backend/backend`: Spring Boot 3.5 (Java 21) backend, described as a SaaS for barbershop management with hexagonal architecture. Maven wrapper scripts (`mvnw`, `mvnw.cmd`) are provided.
- `barbermind-backend/backend/compose.yaml`: Lightweight Compose file for local Postgres and Redis to support backend development.
- `barbermind-frontend`: Angular 21 application with a standalone root component and the default Angular starter template.

## Common commands

### Backend (Spring Boot / Maven)

All commands assume the working directory is `barbermind-backend/backend`.

- Build the backend (runs tests):
  - Unix-like shells:
    - `./mvnw clean package`
  - Windows / PowerShell:
    - `./mvnw.cmd clean package` (or run `mvnw.cmd` directly)

- Run the backend in development mode:
  - Unix-like: `./mvnw spring-boot:run`
  - Windows: `./mvnw.cmd spring-boot:run`

- Run all backend tests:
  - Unix-like: `./mvnw test`
  - Windows: `./mvnw.cmd test`

- Run a single JUnit test class (example):
  - Unix-like: `./mvnw -Dtest=BarbermindBackendApplicationTests test`
  - Windows: `./mvnw.cmd -Dtest=BarbermindBackendApplicationTests test`

- Run a single test method:
  - `./mvnw -Dtest=ClassName#methodName test`

### Frontend (Angular)

All commands assume the working directory is `barbermind-frontend`.

- Install dependencies:
  - `npm install`

- Start the dev server (from README):
  - `npm start` (runs `ng serve`)
  - Alternatively, if you prefer using the CLI directly: `npx ng serve`

- Build the frontend:
  - `npm run build`

- Run all unit tests:
  - `npm test` (runs `ng test` with Karma)

- Run tests for a specific spec file (example):
  - `npx ng test --include src/app/app.spec.ts`

### Docker / infrastructure

From the repository root:

- Bring up Postgres and backend via the root Compose file:
  - `docker compose up postgres backend`

- Bring up only Postgres (for local development without running the backend container):
  - `docker compose up postgres`

From `barbermind-backend/backend` (using its local `compose.yaml`):

- Start Postgres and Redis services used by the backend during development:
  - `docker compose up`

## Backend architecture (hexagonal / DDD)

The backend follows an early hexagonal architecture for the booking context:

- Entry point:
  - `BarbermindBackendApplication` is the Spring Boot entry class (`com.barbermind.backend.BarbermindBackendApplication`).

- Booking domain model (`booking/domain/model`):
  - `Appointment` is the core domain entity/aggregate for a barbershop appointment, with:
    - Strongly-typed identifiers (`UUID` for appointment, customer, salon, employee).
    - Temporal information (`startTime`, `endTime`).
    - Economic data (`price`).
    - Lifecycle state via `AppointmentStatus` (values like `PENDING`, `CONFIRMED`, `COMPLETED`, `CANCELLED`, `NO_SHOW`).
  - `Appointment.create(...)` is the factory method that enforces domain invariants (non-null relationships, future start times, non-negative price) and computes `endTime` based on `durationInMinutes`.

- Domain ports (hexagonal boundaries):
  - Input port in `booking/domain/port/in`:
    - `CreateAppointmentUseCase` describes the operation available to the outside world for creating appointments: `UUID createAppointment(CreateAppointmentCommand command)`.
  - Output port in `booking/domain/port/out`:
    - `AppointmentRepositoryPort` abstracts persistence for `Appointment`:
      - `save(Appointment appointment)` for create/update.
      - `findById(UUID id)` returning an `Optional<Appointment>`.
      - `findByEmployeeAndDataRange(UUID employeeId, LocalDate startDate, LocalDate endDate)` for querying schedules.

- Application layer:
  - DTO/command in `booking/application/dto`:
    - `CreateAppointmentCommand` is a record containing all data required to create an appointment (customer, salon, employee, service, start time, duration, price).
  - Service in `booking/application/service`:
    - `AppointmentService` is annotated with `@Service` and implements `CreateAppointmentUseCase`.
    - It depends on `AppointmentRepositoryPort` (constructor injection), acting as the application service that will:
      - Translate the `CreateAppointmentCommand` into a domain-level `Appointment` (via `Appointment.create(...)`).
      - Persist it through the repository port.
    - `createAppointment` is currently a stub (`return null;`); future work should create the domain object and save it.

- Persistence and adapters:
  - At this stage there are no concrete adapter implementations for `AppointmentRepositoryPort`; they are expected to live in an infrastructure/persistence package that uses Spring Data JPA with PostgreSQL.

- Configuration:
  - `application.properties` currently defines only `spring.application.name=barbermind-backend`.
  - Database and infrastructure configuration are intended to be provided via Docker Compose and Spring Boot’s Docker Compose integration:
    - Postgres container (`postgres` service) with `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASS` environment variables.
  - Maven dependencies include Spring Data JPA, Spring Security, Redis, validation, web, actuator, and PostgreSQL driver, indicating planned use of security, caching, and monitoring, even though configuration code is not yet present.

When extending the backend, follow the existing pattern:

- Define domain entities and value objects under `booking/domain/model`.
- Define input ports for new use cases in `booking/domain/port/in`.
- Define output ports (repositories, external services) in `booking/domain/port/out`.
- Introduce application services in `booking/application/service` that implement input ports and coordinate domain logic plus persistence via output ports.

## Frontend architecture (Angular 21)

The frontend is a recent Angular 21 app using standalone components and the new control-flow syntax.

- Entry point and root component:
  - `src/app/app.ts` defines the `App` root component using the standalone style, importing `RouterOutlet` and referencing external HTML/SCSS (`app.html`, `app.scss`).
  - The root component exposes a `title` signal that is currently used in the starter template.

- Application configuration:
  - `src/app/app.config.ts` exports `appConfig: ApplicationConfig` which:
    - Registers global error listeners via `provideBrowserGlobalErrorListeners()`.
    - Registers routing via `provideRouter(routes)` using `app.routes.ts`.

- Routing:
  - `src/app/app.routes.ts` currently exports an empty `Routes` array.
  - Feature areas should be added as route entries here, ideally using lazy-loaded routes for larger features.

- Template:
  - `src/app/app.html` contains the default Angular welcome page markup plus a `<router-outlet />` at the bottom; it is intended to be replaced by the real application shell.

- Build & tooling:
  - `package.json` defines the core scripts (`start`, `build`, `watch`, `test`) and depends on `@angular/cli`, `@angular/build`, `typescript`, `vitest`, and `jsdom` for tests.

## Angular and TypeScript conventions (`barbermind-frontend/.gemini/GEMINI.md`)

The `.gemini/GEMINI.md` file encodes conventions that should also guide Warp when editing frontend code:

- TypeScript:
  - Use strict typing and prefer type inference when the type is obvious.
  - Avoid `any`; use `unknown` when a type is uncertain.

- Angular structure and state:
  - Use standalone components (no NgModules) and do not set `standalone: true` explicitly in decorators, as it is the default.
  - Use Angular signals for state management (`signal`, `computed`) and avoid mutating state directly; use `.set`/`.update` instead of `mutate`.
  - Use lazy loading for feature routes where appropriate.

- Components and templates:
  - Prefer `input()` and `output()` functions instead of the classic `@Input`/`@Output` decorators.
  - Set `changeDetection: ChangeDetectionStrategy.OnPush` on components for performance.
  - Prefer inline templates only for very small components; otherwise keep the existing external template organization.
  - Use the new control-flow syntax (`@if`, `@for`, `@switch`) instead of `*ngIf`, `*ngFor`, `*ngSwitch`.
  - Do not use `ngClass`/`ngStyle`; instead, prefer explicit `class.*` and `style.*` bindings.

- Services and DI:
  - Design services with a single responsibility and provide them using `providedIn: 'root'` for singletons.
  - Use the `inject()` function for dependency injection instead of constructor injection where practical.

- Assets and images:
  - Use `NgOptimizedImage` for static images (it does not work with inline base64 images).

These conventions should be preserved and followed when generating or editing Angular/TypeScript code in this repository.
