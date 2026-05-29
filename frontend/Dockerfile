# Build stage
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build:production

# Serve stage
FROM node:18-alpine
WORKDIR /app
RUN npm install -g @angular/cli
COPY --from=build /app/dist/svh-frontend ./dist/svh-frontend
EXPOSE 4200
CMD ["npm", "start"]

