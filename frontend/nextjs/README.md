# Pokedex Frontend

A modern **Pokédex and trainer dashboard** built with **Next.js**, **React**, **TypeScript**, and **Tailwind CSS**.

This project provides a clean, responsive frontend experience for trainer authentication, dashboard navigation, and
browsing Pokémon-related information. It is designed as a lightweight web application that combines a polished UI with a
straightforward app structure.

## Overview

The Pokedex Frontend is a client-side application focused on delivering an intuitive trainer experience. After signing
in, users can navigate through dashboard sections to explore Pokédex data, review caught Pokémon, and view
trainer-related information.

This project emphasizes:

- clean component-based UI design
- modern React and Next.js patterns
- responsive styling with Tailwind CSS
- maintainable TypeScript-based frontend architecture

## Features

- **Trainer login flow**
- **Dashboard-based navigation**
- **Pokédex browsing interface**
- **Caught Pokémon view**
- **Leagues and Gym section**
- **Responsive UI styling**
- **App Router structure with Next.js**

## Tech Stack

- **Next.js**
- **React**
- **TypeScript**
- **Tailwind CSS**
- **pnpm**
- **ESLint**

## Project Structure

The application is located in the `nextjs` directory.

```text
nextjs/ 
├── app/ 
├── public/ 
├── package.json 
├── tsconfig.json 
├── postcss.config.mjs 
└── README.md
```

## Getting Started

### Prerequisites

Make sure you have the following installed:

- **Node.js**
- **pnpm**

## Installation

From the project root:

```bash
cd nextjs pnpm install
```

## Running the App Locally

Start the development server:

```bash
pnpm dev
```

Then open:

```text
http://localhost:3000/
```

## Production Build

To create a production build:

```bash
pnpm build
```

To start the production server:

```bash
pnpm start
```

## Linting

Run the linter with:

```bash
pnpm lint
```

## Environment Configuration

If the application depends on environment variables, configure them in `.env` or `.env.local` before running the app.

Example:

```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
```

Replace the example value with the correct backend API URL for your environment.

## Docker

A `Dockerfile` is included for containerized builds.

Build the image:

```bash
docker build -t pokedex-frontend .
```

Run the container:

```bash
docker run -p 3000:3000 pokedex-frontend
```

## Development Notes

- Use **pnpm** for package management.
- Run all commands from the **`nextjs`** directory.
- If dependency resolution issues occur, reinstall dependencies from the app directory.

## Goals of the Project

This project was built to demonstrate:

- full-stack frontend application structure with Next.js
- reusable component design
- client-side routing and layout organization
- modern styling practices with Tailwind CSS
- a themed product experience based on Pokémon data

## Future Improvements

Potential next steps for the project include:

- search and filtering for Pokémon entries
- richer trainer profile data
- pagination or infinite scrolling for large result sets
- improved error handling and loading states
- test coverage for components and application flows

## Summary

The Pokedex Frontend is a polished web application that showcases a modern React-based architecture in a fun, familiar
product theme. It combines practical dashboard functionality with a clean UI and serves as a solid example of a
TypeScript-first Next.js project.