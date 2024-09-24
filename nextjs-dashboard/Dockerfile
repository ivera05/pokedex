# Step 1: Base image
FROM node:current-alpine AS base

# Set working directory
WORKDIR /app

# Install pnpm globally
RUN npm install -g pnpm

# Copy over package.json and pnpm-lock.yaml
COPY package.json pnpm-lock.yaml ./

# Install dependencies
RUN pnpm install --frozen-lockfile

# Copy the rest of the application code
COPY . .

# Build the Next.js app
RUN pnpm build

# Step 2: Production image
FROM node:current-alpine AS production

# Set working directory
WORKDIR /app

# Install pnpm globally
RUN npm install -g pnpm

# Copy the build output and node_modules from the previous stage
COPY --from=base /app/node_modules ./node_modules
COPY --from=base /app/.next ./.next
COPY --from=base /app/package.json ./package.json

# Expose the port the app runs on
EXPOSE 3000

# Start the Next.js app
CMD ["pnpm", "dev"]
