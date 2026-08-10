# NexusTrack

A SaaS project management platform built for engineering teams. NexusTrack helps teams plan, track, and deliver software projects with powerful integrations and reporting.

## Features

- **Project & Task Tracking** — Organize work into projects with customizable task boards, priorities, assignees, and due dates. Track progress across sprints and milestones.
- **Excel Report Generation** — Export detailed project reports, timesheets, and analytics as Excel spreadsheets for stakeholders and audits.
- **Webhook Integrations** — Connect NexusTrack with your existing tools through configurable webhooks for real-time event-driven workflows.
- **File Attachments** — Attach documents, images, and other files directly to tasks and projects for centralized reference.
- **Team Notifications** — Keep everyone in the loop with email and Slack notifications for task assignments, status changes, comments, and deadlines.

## TechStack

| Layer                | Technology                  |
| -------------------- | --------------------------- |
| Backend API          | Java 11 / Spring Boot       |
| Frontend             | React / TypeScript           |
| Notification Service | Node.js / Express            |
| Database             | PostgreSQL 14                |
| Monorepo Tooling     | npm workspaces               |

## Project Structure

```
NexusTrack/
├── backend/                  # Spring Boot API and report services
├── frontend/                 # React/TypeScript SPA
├── notification-service/     # Express notification microservice
├── packages/                 # Shared npm packages
├── docker-compose.yml        # Local development environment
└── .github/workflows/        # CI pipeline
```

## Getting Started

### Prerequisites

- Java 11+
- Node.js 16+
- Docker & Docker Compose
- Maven

### Run with Docker Compose

```bash
docker-compose up --build
```

This starts all services:

| Service              | URL                        |
| -------------------- | -------------------------- |
| API Service          | http://localhost:8080       |
| Report Service       | http://localhost:8081       |
| Notification Service | http://localhost:3001       |
| PostgreSQL           | localhost:5432              |

### Development

Install frontend and notification service dependencies:

```bash
npm install
```

Run backend tests:

```bash
mvn -f backend/pom.xml clean verify
```

Run frontend tests:

```bash
npx --workspace=frontend react-scripts test
```

Run notification service tests:

```bash
cd notification-service && npm test
```

## License

Proprietary — All rights reserved.
