<div align="center">

# BeatBank

**A full-stack music catalog platform for managing songs, albums, genres and artists.**

<img src="https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white"></img>
<img src="https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=black"></img>
<img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white"></img>
<img src="https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=white"></img>
<img src="https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white"></img>
<img src="https://img.shields.io/badge/Swagger%20UI-85EA2D?logo=swagger&logoColor=black"></img>
<img src="https://img.shields.io/badge/Vue.js-3-4FC08D?logo=vuedotjs&logoColor=white"></img>
<img src="https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=white"></img>
<img src="https://img.shields.io/badge/Vite-646CFF?logo=vite&logoColor=white"></img>
<img src="https://img.shields.io/badge/Tailwind%20CSS-4-06B6D4?logo=tailwindcss&logoColor=white"></img>
<img src="https://img.shields.io/badge/Vue%20Router-42b883?logo=vuedotjs&logoColor=white"></img>
<img src="https://img.shields.io/badge/License-MIT-green"></img>

<img width="1728" height="908" alt="Captura de pantalla 2026-09-10 a las 1 00 34" src="https://github.com/user-attachments/assets/83301a7a-86ef-4b74-b646-8adcab24847f" />
<img width="1728" height="908" alt="Captura de pantalla 2026-09-10 a las 1 00 50" src="https://github.com/user-attachments/assets/d563333f-3266-44b5-82d1-e68fa11cbf04" />
<img width="1728" height="908" alt="Captura de pantalla 2026-09-10 a las 1 00 59" src="https://github.com/user-attachments/assets/5e177497-c18e-430e-a39e-bcdaea48d86a" />


</div>

## About

**BeatBank** is a modern full-stack web application for cataloging a music library. It lets you store, organize, search and explore **songs**, **albums**, **genres** and **artists** through a clean REST API backed by a dark, glassmorphic dashboard-style web UI.

The backend follows a **hexagonal (ports & adapters) architecture**, keeping the domain model decoupled from frameworks and persistence, while the frontend is built with the modern Vue 3 + Vite + Tailwind stack. It also ships with Swagger UI, per-IP rate limiting and enforced test coverage.

---

## Features

### Backend

- **Full CRUD** for songs, albums, artists and genres
- **Full-text song search** (`/api/songs/search?q=...`)
- **Pagination & sorting** via Spring Data `Pageable`
- **Rate limiting** — Bucket4j in-memory token bucket (100 requests/minute per IP)
- **Swagger UI / OpenAPI** docs exposed at `/swagger-ui.html`


### Frontend

- **Dashboard** with live counts, top songs, latest albums and genre browsing
- **Song gallery** with pagination and sortable by plays
- **Dedicated album & artist views**
- Responsive **dark UI** built with Tailwind CSS 4 and Vue 3 `<script setup>`

---

## Tech Stack

| Layer      | Technology                                                               |
| ---------- | ------------------------------------------------------------------------ |
| Backend    | Java 25, Spring Boot 4, Spring Data JPA (Hibernate), Lombok              |
| Persistence| MySQL (production), H2 (dev / tests)                                     |
| API Docs   | Springdoc OpenAPI + Swagger UI                                           |
| Security   | Bucket4j rate limiting                                                   |
| Frontend   | Vue 3, TypeScript, Vite, Vue Router, Tailwind CSS 4                      |
| Tooling    | Maven, pnpm, JaCoCo                                                     |

## Getting Started

### Prerequisites

- [JDK 25](https://www.oracle.com/java/technologies/downloads/) (or compatible)
- [Node.js](https://nodejs.org) 20+
- [pnpm](https://pnpm.io)
- [MySQL](https://www.mysql.com) (optional, H2 in-memory DB works out of the box)

### 1. Backend (Spring Boot)

```bash
cd Backend/BeatBank
./mvnw spring-boot:run        # or: .\mvnw.cmd spring-boot:run on Windows
```

The API is then available at **<http://localhost:8080>** and Swagger UI at **<http://localhost:8080/swagger-ui.html>**.

> By default the app boots with an in-memory H2 database seeded via `data.sql`. To use MySQL, point `spring.datasource.url`, `username` and `password` in `src/main/resources/application.properties`.

### 2. Frontend (Vue 3)

```bash
cd Frontend/beatbank-frontend
pnpm install
pnpm dev
```

The UI is served by Vite and proxies `/api` requests to `http://localhost:8080`, so no extra CORS setup is needed. Open **<http://localhost:5173>**.

---

## API Overview

All endpoints live under `/api` and return paginated JSON where applicable.

| Method   | Endpoint                                | Description                        |
| -------- | --------------------------------------- | ---------------------------------- |
| `GET`    | `/api/songs`                            | Paginated list of songs            |
| `GET`    | `/api/songs/search?q=`                  | Full-text search on song names     |
| `GET`    | `/api/songs/{id}`                       | Fetch a single song                |
| `POST`   | `/api/songs`                            | Create a song                      |
| `PUT`    | `/api/songs/{id}`                       | Replace a song                     |
| `PATCH`  | `/api/songs/{id}`                       | Partially update a song            |
| `DELETE` | `/api/songs/{id}`                       | Delete a song                      |
| `POST`   | `/api/songs/{id}/play`                  | Increment play count               |
| `POST`   | `/api/songs/{songId}/artists/{artistId}`| Link an artist to a song           |
| `DELETE` | `/api/songs/{id}/artists/{artistId}`    | Unlink an artist from a song       |
| `GET/POST` | `/api/albums`, `/api/artists`, `/api/genres` | List / create each entity   |
| `GET/PUT/PATCH/DELETE` | `/api/{resource}/{id}`      | Read / update / delete an entity   |

The full interactive reference (including request/response schemas) is available in **Swagger UI**.
