# 1. Dependencies Stage
FROM node:25.6.1-alpine AS deps
RUN apk add --no-cache libc6-compat
WORKDIR /app

# Install pnpm directly (Corepack is no longer bundled in Node 25)
RUN npm install -g pnpm@latest

COPY package.json pnpm-lock.yaml ./

# We use --no-frozen-lockfile here because you're in the middle of a version migration
# This allows pnpm to update the lockfile inside the container if needed
RUN --mount=type=cache,id=pnpm,target=/pnpm/store pnpm install --no-frozen-lockfile

# 2. Build Stage
FROM node:25.6.1-alpine AS builder
WORKDIR /app
# Install pnpm here too for the build command
RUN npm install -g pnpm@latest
COPY --from=deps /app/node_modules ./node_modules
COPY .. .

ENV NEXT_TELEMETRY_DISABLED 1
RUN pnpm build

# 3. Production Stage
FROM node:25.6.1-alpine AS runner
WORKDIR /app

ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DISABLED=1
ENV HOSTNAME="0.0.0.0"
ENV PORT=3000

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

RUN mkdir .next && chown nextjs:nodejs .next

COPY --from=builder /app/public ./public
COPY --from=builder --chown=nextjs:nodejs /app/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /app/.next/static ./.next/static

USER nextjs
EXPOSE 3000

CMD ["node", "server.js"]