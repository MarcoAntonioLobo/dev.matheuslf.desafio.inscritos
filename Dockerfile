# ==========================
# Stage 1: Build Maven
# ==========================
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ==========================
# Stage 2: Imagem final Java
# ==========================
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY wait-for-it.sh wait-for-it.sh

RUN apt-get update && apt-get install -y dos2unix \
    && dos2unix wait-for-it.sh \
    && chmod +x wait-for-it.sh \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "db:5432", "--", "java", "-jar", "app.jar"]
